package org.rookie.config;

import lombok.Getter;

@Getter
public enum BusinessExceptionEnum {

    NOT_FIND_IN_DATABASE(10001,"空值查询"),
    GET_FILED_FLIED(10002, "无法获取字段反射值"),
    SERVICE_OVERLOADED(10003,"服务繁忙,请求被熔断");


    private final int code;
    private final String message;

    BusinessExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    //用于直接抛出枚举异常
    public BusinessException exception(String customMessage, Throwable cause) {
        return new BusinessException(this.code, customMessage, cause);
    }

    public BusinessException exception() {
        return new BusinessException(this.code, this.message);
    }
}
