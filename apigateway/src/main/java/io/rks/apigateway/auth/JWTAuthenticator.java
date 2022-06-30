package io.rks.apigateway.auth;

import com.google.common.base.Strings;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import io.rks.apigateway.models.AuthResult;
import io.rks.apigateway.models.BaseApiRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;

@Component
public class JWTAuthenticator implements Authenticator {
    Logger logger = LogManager.getLogger(JWTAuthenticator.class);

    @Override
    public AuthResult authenticate(BaseApiRequest authRequest) {
        if (logger.isDebugEnabled()) {
            logger.debug("authenticate with credentials " +
                            " type=" + authRequest.getAuthMethod() +
                            " authorization=" + authRequest.getAuthorization() +
                            " traceId=" + authRequest.getTraceId() +
                            " timestamp=" + authRequest.getRksTimestamp());
        }

        AuthResult authResult = new AuthResult();
        authResult.setValid(false);

        final String token = getBearerTokenFromRequest(authRequest);
        // validating jwt token
        if (Strings.isNullOrEmpty(token)) {
            authResult.setErrorCode("GNAUE0002");
            return authResult;
        } else {
            try {
                if (isTokenExpired(token)) {
                    authResult.setErrorCode("GNAUE0003");
                    return authResult;
                }
            } catch (ParseException e) {
                logger.error("Error in parsing jwt token for traceId={}", authRequest.getTraceId());
                authResult.setErrorCode("GNAUE0002");
                return authResult;
            }
        }
        //auth result valid
        authResult.setValid(true);
        return authResult;
    }

    private String getBearerTokenFromRequest(BaseApiRequest request) {
        String token;
        String authorization = request.getAuthorization();

        if (authorization != null && authorization.length()>7) {
            String tokenType = authorization.substring(0,6);
            if (tokenType.equals("Bearer")) {
                token = authorization.substring(7);
                return token;
            }
        }
        return null;
    }

    private boolean isTokenExpired(String token) throws ParseException {
        JWT jwt;
        jwt = JWTParser.parse(token);
        //verifying expiry
        Date now = new Date();
        return now.before(jwt.getJWTClaimsSet().getExpirationTime());
    }
}
