package io.rks.apigateway.utils;

import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import com.nimbusds.jwt.proc.BadJWTException;
import com.nimbusds.jwt.proc.DefaultJWTClaimsVerifier;
import com.nimbusds.jwt.proc.JWTClaimsSetVerifier;
import com.rks.mcommon.exceptions.UnauthorizedAccessException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.crypto.DefaultJwtSignatureValidator;
import io.rks.apigateway.exceptions.ApiUnauthorizedException;
import io.rks.apigateway.security.KeyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.google.common.base.Strings;

import javax.crypto.spec.SecretKeySpec;
import java.text.ParseException;
import java.util.*;

import static io.rks.apigateway.constants.ExceptionConstants.*;

@Component
public class JwtUtil {
    Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    private String secret;

    public Claims getAllClaimsFromToken(String token) {
        //Claims claims = Jwts.parser().setSigningKey(KeyManager.getKeycloakPublicKey()).parseClaimsJws(token).getBody();
        Jws<Claims> jws = Jwts.parser().setSigningKey(KeyManager.getKeycloakPublicKey()).parseClaimsJws(token);
        Claims claims = null;
        Set<String> keySet = claims.keySet();
        if (logger.isDebugEnabled()) {
            for (String key : keySet) {
                logger.info("Key: " + key + "::" + "Value: " + claims.get(key));
            }
        }
        return claims;
    }

    public boolean isInvalid(String token) {
        try {
            JWT jwt = JWTParser.parse(token);
            jwt.getJWTClaimsSet().getClaims().entrySet().forEach(stringObjectEntry -> {
                logger.info(stringObjectEntry.getKey(), stringObjectEntry.getValue());
            });
            return jwt.getJWTClaimsSet().getExpirationTime().before(new Date());
            //Assert.isTrue(jwt.getJWTClaimsSet().getExpirationTime().before(new Date()), "Token expired");
            //return true;
        } catch (ParseException e) {
            logger.error("Error in JWT token parsing. Message=" + e.getMessage());
            throw new ApiUnauthorizedException(401, JWT_TOKEN_PARSE_ERROR, e.getMessage());
        }
    }


}
