package org.rookie.business.exception;

import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import org.rookie.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 统一处理OpenFeign的异常请求情况,是没有成功被处理为Result的响应结果
 */
@Component
public class BusinessFeignErrorDecoder implements ErrorDecoder {

    private static final Logger log = LoggerFactory.getLogger(BusinessFeignErrorDecoder.class);
    private final ErrorDecoder defaultErrorDecoder = new Default();
    
    @Override
    public Exception decode(String methodKey, Response response) {
        log.error("Feign调用出现非预期状态码: {}",response.status() );
        String errorMsg = "";
        try {
            if(response.body() != null) {
               errorMsg= Util.toString(response.body().asReader(Util.UTF_8));
            }
        }catch (IOException e){
            log.error("无法从Feign响应中获取错误信息", e);
        }
        
        // 将 HTTP 状态码和响应体封装进自定义异常
        if (response.status() >= 400 && response.status() <= 499) {
            return new DownstreamServiceException(response.status(), errorMsg);
        }

        // 如果是 5xx 错误，抛出服务器异常
        if (response.status() >= 500 && response.status() <= 599) {
            return new DownstreamServiceException(response.status(), "下游服务器发生错误");
        }
        return defaultErrorDecoder.decode(methodKey, response);
    }
    
    
}
