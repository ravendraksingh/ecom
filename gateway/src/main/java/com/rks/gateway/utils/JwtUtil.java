package com.rks.gateway.utils;

import com.rks.gateway.security.KeyManager;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;

@Component
public class JwtUtil {
    Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    private String secret;
    public Claims getAllClaimsFromToken(String token) {
        //return Jwts.parser().setSigningKey("JWTSuperSecretKey").parseClaimsJws(token).getBody();
        Claims claims = Jwts.parser().setSigningKey(KeyManager.getKeycloakPublicKey()).parseClaimsJws(token).getBody();
        Set<String> keySet = claims.keySet();
        if (logger.isDebugEnabled()) {
            for (String key : keySet) {
                logger.info("Key: " + key + "::" + "Value: " + claims.get(key));
            }
        }
        return claims;
    }

    private boolean isTokenExpired(String token) {
        return this.getAllClaimsFromToken(token).getExpiration().before(new Date());
    }

    public boolean isInvalid(String token) {
        return this.isTokenExpired(token);
    }


}
