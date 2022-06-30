package io.rks.apigateway.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

import static io.rks.apigateway.constants.ExceptionConstants.*;

@Component
public class ApiExceptionFactory {
    Logger logger = LogManager.getLogger(ApiExceptionFactory.class);

    private static Map<String, ApiException> API_EXCEPTION_MAP = new HashMap<>();

    public static ApiException get(String errorCode) {
        return API_EXCEPTION_MAP.get(errorCode);
    }

    @PostConstruct
    void init() {
        API_EXCEPTION_MAP.put("GNAUE0001", new ApiException(401, "auth_error", AUTHORIZATION_HEADER_MISSING));
        API_EXCEPTION_MAP.put("GNAUE0002", new ApiException(401, "auth_error", JWT_TOKEN_INVALID));
        API_EXCEPTION_MAP.put("GNAUE0003", new ApiException(401, "auth_error", JWT_TOKEN_EXPIRED));
    }
}
