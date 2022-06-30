package com.rks.gateway.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class GlobalException extends ResponseStatusException {
    private static final long serialVersionUID = -534124059720870720L;

    public GlobalException(HttpStatus status) {
        super(status);
    }
    public GlobalException(HttpStatus status, String reason) {
        super(status, reason);
    }
    public GlobalException(HttpStatus status, String reason, Throwable cause) {
        super(status, reason, cause);
    }
}
