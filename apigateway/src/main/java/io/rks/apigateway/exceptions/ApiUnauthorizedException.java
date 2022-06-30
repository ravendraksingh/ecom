package io.rks.apigateway.exceptions;

public class ApiUnauthorizedException extends ApiException {

    public ApiUnauthorizedException(int status, String error, String message) {
        super(status, error, message);
    }
}
