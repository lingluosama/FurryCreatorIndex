package org.rookie.business.exception;

import lombok.extern.slf4j.Slf4j;
import org.rookie.consts.Result;
import org.rookie.exception.BusinessException;
import org.rookie.config.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.messaging.handler.annotation.support.MethodArgumentTypeMismatchException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.net.BindException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    
    @ExceptionHandler(value = BusinessException.class)
    public Result<String>handleBusinessException(BusinessException e) {
        //log.warn("业务内异常: code={} msg={}", e.getCode(), e.getMessage());
        return Result.failed(HttpStatus.BAD_REQUEST,e.getMessage());
        
    }


    
    @ExceptionHandler(DownstreamServiceException.class)
    public Result<String> handleDownstreamServiceException(DownstreamServiceException e) {
        log.warn("下游服务异常已被全局处理并返回");    
        return Result.failed(HttpStatus.BAD_REQUEST,e.getMessage());
    }

    /**
     * 处理 404 Not Found 异常
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public Result<String> handleNoHandlerFoundException(NoHandlerFoundException e) {
        return Result.failed(HttpStatus.NOT_FOUND, "请求的API地址不存在");
    }

    /**
     * 处理 405 Method Not Allowed 异常
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result<String> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return Result.failed(HttpStatus.METHOD_NOT_ALLOWED, "不支持该请求方法");
    }


    /**
     * 处理请求体格式错误
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return Result.failed(HttpStatus.BAD_REQUEST, "请求体格式错误或不完整");
    }

    /**
     * 处理方法参数类型不匹配
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Result<String> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return Result.failed(HttpStatus.BAD_REQUEST, "参数类型不匹配");
    }

    /**
     * 处理缺少请求头异常
     */
    @ExceptionHandler(MissingRequestHeaderException.class)
    public Result<String> handleMissingRequestHeaderException(MissingRequestHeaderException e) {
        return Result.failed(HttpStatus.BAD_REQUEST, "缺少必要的请求头: " + e.getHeaderName());
    }

    /**
     * 处理所有未被其他处理器捕获的异常
     */
    @ExceptionHandler(Exception.class)
    public Result<String> handleSystemException(Exception e) {
        log.error("未预期的异常: ", e);
        return Result.failed(HttpStatus.INTERNAL_SERVER_ERROR, "系统繁忙，请稍后重试");
    }
    
}
