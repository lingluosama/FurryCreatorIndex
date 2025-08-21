package org.rookie.business.exception;

import lombok.Getter;

@Getter
public class DownstreamServiceException extends RuntimeException {
    private final int status;
    private final String body;

    public DownstreamServiceException(int status, String body) {
        super("下游服务异常: " + status + " - " + body);
        this.status = status;
        this.body = body;
    }
}