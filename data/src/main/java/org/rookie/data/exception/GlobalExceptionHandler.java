package org.rookie.data.exception;

import cn.hutool.core.io.resource.NoResourceException;
import lombok.extern.slf4j.Slf4j;
import org.rookie.config.ErrorResponse;
import org.rookie.consts.Result;
import org.rookie.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    
    @ExceptionHandler(value = BusinessException.class)
    public Result<Void>handleBusinessException(BusinessException e) {
        log.warn("数据服务业务内异常: code={} msg={}", e.getCode(), e.getMessage());

        ErrorResponse error = new ErrorResponse(e.getCode(), e.getMessage());
        return Result.failed(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        
    }

    @ExceptionHandler(NoResourceException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceException(NoResourceException e) {
        log.warn("数据服务找不到资源: ", e);
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(Exception.class)
    public Result<String> handleSystemException(Exception e) {
        log.error("数据服务怎么鼠了: ", e);
        return Result.failed(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
    }
    
}
