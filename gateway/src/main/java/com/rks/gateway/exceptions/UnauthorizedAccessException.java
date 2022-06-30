package com.rks.gateway.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UnauthorizedAccessException extends ResponseStatusException {

    public UnauthorizedAccessException(HttpStatus status) {
        super(status);
    }

    public UnauthorizedAccessException(HttpStatus status, String reason) {
        super(status, reason);
    }
}
