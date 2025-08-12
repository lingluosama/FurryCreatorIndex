package org.rookie.data.service.impl;

import io.minio.*;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.MinioException;
import lombok.RequiredArgsConstructor;
import org.rookie.data.service.OssService;
import org.rookie.exception.BusinessException;
import org.rookie.exception.BusinessExceptionEnum;
import org.rookie.model.bo.FileInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class OssServiceImpl implements OssService {

    private final MinioClient minioClient;

    @Value("${minio.bucket-name}")
    private String bucketName;
    @Value("${minio.endpoint}")
    private String endPoint;

    @Override
    public FileInfo uploadFile(MultipartFile file) {
        if(file == null){
            return null;
        }

        try {
            boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if(!bucketExists){
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }

            String originalFilename = file.getOriginalFilename();
            String extension = "";
            if(originalFilename != null && originalFilename.contains(".")){
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String objectName = UUID.randomUUID().toString().replace("-", "") + extension;

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );

            String fileUrl = endPoint + "/" + bucketName + "/" + objectName;

            FileInfo fileInfo = new FileInfo();
            fileInfo.setName(originalFilename);
            fileInfo.setUrl(fileUrl);
            return fileInfo;

        } catch (MinioException e) {
            // 捕获Minio服务相关异常，确认为服务不可用，返回繁忙异常
            throw BusinessExceptionEnum.SERVICE_OVERLOADED.exception(e.getMessage(), e);
        } catch (IOException e) {
            // 捕获IOException，特别是SocketTimeoutException等网络问题，可视为服务不可用
            if (e instanceof SocketTimeoutException) {
                throw BusinessExceptionEnum.SERVICE_OVERLOADED.exception("Minio服务连接超时", e);
            }
            // 抛出文件流读取异常,服务端问题
            throw new BusinessException("文件流读取失败", e);
        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
            // 捕获与凭证或算法相关的异常
            throw new BusinessException("Minio客户端配置错误", e);
        }
    }

    @Override
    public boolean deleteFile(String filePath) {
        try {
            String objectName;
            try {
                String path = new URL(filePath).getPath();
                // 确保路径解析逻辑正确，截取从桶名之后的部分作为objectName
                objectName = path.substring(path.indexOf("/" + bucketName + "/") + bucketName.length() + 2);
            } catch (MalformedURLException e) {
                // URL格式错误，抛出自定义异常
                throw new BusinessException("文件URL格式不正确", e);
            }

            minioClient.removeObject(
                    RemoveObjectArgs
                            .builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            );
            return true;

        } catch (MinioException e) {
            // 同上传操作，将Minio服务异常转换为自定义异常
            throw BusinessExceptionEnum.SERVICE_OVERLOADED.exception(e.getMessage(), e);
        } catch (IOException e) {
            // 同上传操作，处理网络异常
            if (e instanceof SocketTimeoutException) {
                throw BusinessExceptionEnum.SERVICE_OVERLOADED.exception("Minio服务连接超时", e);
            }
            throw new BusinessException("文件删除失败", e);
        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
            throw new BusinessException("Minio客户端配置错误", e);
        }
    }
}