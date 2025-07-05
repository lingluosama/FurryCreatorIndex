package org.rookie.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private int code;
    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }
    //处理链式调用,使其支持throw
    public BusinessException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}
