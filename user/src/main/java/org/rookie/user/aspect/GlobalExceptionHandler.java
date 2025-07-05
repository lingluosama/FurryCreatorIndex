package org.rookie.user.aspect;

import lombok.extern.slf4j.Slf4j;
import org.rookie.config.BusinessException;
import org.rookie.config.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    
    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<ErrorResponse>handleBusinessException(BusinessException e) {
        log.warn("用户服务业务内异常: code={} msg={}", e.getCode(), e.getMessage());

        ErrorResponse error = new ErrorResponse(e.getCode(), e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleSystemException(Exception e) {
        log.error("用户服务怎么鼠了: ", e);
        ErrorResponse error = new ErrorResponse(500, "系统繁忙");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}
