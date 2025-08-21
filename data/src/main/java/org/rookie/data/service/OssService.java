package org.rookie.data.service;

import org.rookie.model.bo.FileInfo;
import org.springframework.web.multipart.MultipartFile;

public interface OssService {

    /**
     * 上传文件
     * @param file 表单文件对象
     * @return 文件信息
     */
    FileInfo uploadFile(MultipartFile file) throws Throwable;

    /**
     * 删除文件
     *
     * @param filePath 文件完整URL
     * @return 删除结果
     */
    boolean deleteFile(String filePath) throws  Throwable;


}
