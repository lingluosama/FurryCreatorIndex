package org.rookie.business.exception;

import lombok.extern.slf4j.Slf4j;
import org.rookie.consts.Result;
import org.rookie.exception.BusinessException;
import org.rookie.config.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    
    @ExceptionHandler(value = BusinessException.class)
    public Result<String>handleBusinessException(BusinessException e) {
        log.warn("用户服务业务内异常: code={} msg={}", e.getCode(), e.getMessage());
        return Result.failed(HttpStatus.BAD_REQUEST,e.getMessage());
        
    }

    @ExceptionHandler(Exception.class)
    public Result<String> handleSystemException(Exception e) {
        log.error("用户服务怎么鼠了: ", e);
        return Result.failed(HttpStatus.INTERNAL_SERVER_ERROR,"系统繁忙，请稍后重试");
    }
    
    @ExceptionHandler(DownstreamServiceException.class)
    public Result<String> handleDownstreamServiceException(DownstreamServiceException e) {
        log.warn("下游服务异常已被全局处理并返回");    
        return Result.failed(HttpStatus.BAD_REQUEST,e.getMessage());
    }
    
}
