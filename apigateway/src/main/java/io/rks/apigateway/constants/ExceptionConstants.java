package io.rks.apigateway.constants;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

public class ExceptionConstants {
    public static final String GENERIC_ERROR_MESSAGE = "Please try after some time";
    public static final String AUTHORIZATION_HEADER_MISSING = "Authorization header is missing";
    public static final String AUTHORIZATION_HEADER_INVALID = "Authorization header is invalid";
    public static final String INTERNAL_SERVER_ERROR = "internal_server_error";
    public static final String UNAUTHORIZED = "unauthorized_request_error";
    public static final String JWT_TOKEN_INVALID = "Invalid JWT token. Error in parsing JWT token.";
    public static final String JWT_TOKEN_PARSE_ERROR = "jwt_parse_failure";
    public static final String JWT_TOKEN_EMPTY = "jwt_token_empty";
    public static final String JWT_TOKEN_EXPIRED = "jwt_token_expired";
}
