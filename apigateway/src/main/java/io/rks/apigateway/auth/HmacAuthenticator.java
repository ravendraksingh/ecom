package io.rks.apigateway.auth;

import auth.HmacUtils;
import io.rks.apigateway.models.AuthResult;
import io.rks.apigateway.models.BaseApiRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class HmacAuthenticator implements Authenticator {
    Logger logger = LoggerFactory.getLogger(HmacAuthenticator.class);

    public static final String CLIENT_SECRET = "Y0kn3KkdeET2dlwgxR";
    @Override
    public AuthResult authenticate(BaseApiRequest request) {
        String sigValue = request.getSignature();
        AuthResult authResult = new AuthResult();

        String hmacValue = HmacUtils.generateRequestHmac(request.getRequestMethod(),
                request.getContentType(),
                request.getAcceptType(),
                request.getTraceId(),
                request.getRksTimestamp(),
                request.getRequestUri(),
                request.getRequestBody(),
                request.getClientId(),
                CLIENT_SECRET
        );

        if (!hmacValue.equalsIgnoreCase(sigValue)) {
            logger.info("Authentication failed for clientId={}, traceId={}, request hmac={}" +
                    ", generated hmac={}",  request.getClientId(), request.getTraceId(), sigValue, hmacValue);
            authResult.setValid(false);
            authResult.setErrorCode("GNAUE0001");
            return authResult;
        }
        return authResult;
    }
}
