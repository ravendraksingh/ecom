package com.rks.gateway.filter;

import com.rks.gateway.utils.JwtUtil;
import com.rks.mcommon.exceptions.UnauthorizedAccessException;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.rks.gateway.config.ExceptionConstants.AUTHORIZATION_HEADER_INVALID;
import static com.rks.gateway.config.ExceptionConstants.AUTHORIZATION_HEADER_MISSING;
import static com.rks.mcommon.constants.CommonConstants.FAILED;

@Component
public class AuthenticationFilter implements GatewayFilter {
    Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        if (isAuthMissing(request)) {
            logger.error("{}, clientIp={}, serverIp={}, requestUri={}, method={}, status={}, message={}",
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:sss").format(new Date()),
                    request.getRemoteAddress(), request.getLocalAddress(), request.getURI(),
                    request.getMethod(), HttpStatus.UNAUTHORIZED.value(), AUTHORIZATION_HEADER_MISSING);
            //throw new UnauthorizedAccessException(FAILED, 401, AUTHORIZATION_HEADER_MISSING);
            //throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, AUTHORIZATION_HEADER_MISSING);
            //throw new RuntimeException(AUTHORIZATION_HEADER_MISSING);
            throw new com.rks.gateway.exceptions.UnauthorizedAccessException(HttpStatus.UNAUTHORIZED,
                    AUTHORIZATION_HEADER_MISSING);
        }
        final String token = getTokenFromRequest(request);
        logger.debug("token value from request: " + token);
        if (jwtUtil.isInvalid(token)) {
            throw new UnauthorizedAccessException(FAILED, 401, AUTHORIZATION_HEADER_INVALID);
        } else {
            return chain.filter(exchange);
        }
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private String getTokenFromRequest(ServerHttpRequest request) {
        String token;
        String authHeader = getAuthHeader(request);

        if (authHeader != null && authHeader.length()>7) {
            String tokenType = getAuthHeader(request).substring(0,6);
            if (tokenType.equals("Bearer")) {
                token = authHeader.substring(7);
                return token;
            }
        }
        return null;
    }
    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty("Authorization").get(0);
    }

    private boolean isAuthMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }
    private void populateRequestWithHeaders(ServerWebExchange exchange, String token) throws Exception {
        Claims claims = jwtUtil.getAllClaimsFromToken(token);
        exchange.getRequest().mutate()
                .header("id", String.valueOf(claims.get("id")))
                .header("role", String.valueOf(claims.get("role")))
                .build();
    }
}
