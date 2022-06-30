/*
package com.rks.gateway.config;

import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilterFactory implements GatewayFilterFactory<AuthenticationFilterFactory.Config> {
    Logger logger = LoggerFactory.getLogger(AuthenticationFilterFactory.class);

    @Autowired
    private JwtUtil jwtUtil;

*/
/*    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        if (this.isAuthMissing(request)) {
            throw new UnAuthorizedException(HttpStatus.UNAUTHORIZED, AUTHORIZATION_HEADER_MISSING);
        }
        final String token = getTokenFromRequest(request);
        if (jwtUtil.isInvalid(token)) {
            throw new UnAuthorizedException(HttpStatus.UNAUTHORIZED, AUTHORIZATION_HEADER_INVALID);
        }
        //this.populateRequestWithHeaders(exchange, token);
        return chain.filter(exchange);
    }*//*


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
    private void populateRequestWithHeaders(ServerWebExchange exchange, String token) {
        Claims claims = jwtUtil.getAllClaimsFromToken(token);
        exchange.getRequest().mutate()
                .header("id", String.valueOf(claims.get("id")))
                .header("role", String.valueOf(claims.get("role")))
                .build();
    }

    @Override
    public GatewayFilter apply(AuthenticationFilterFactory.Config config) {
        return null;
    }

//    @Override
//    public GatewayFilter apply(Config config) {
//        return (exchange, chain) -> {
//            logger.info("in AuthenticationFilterFactory:apply");
//            ServerHttpRequest request = exchange.getRequest();
//            if (this.isAuthMissing(request)) {
//                throw new UnAuthorizedException(HttpStatus.UNAUTHORIZED, "Authorization header is missing");
//            }
//            final String token = getTokenFromRequest(request);
//            if (jwtUtil.isInvalid(token)) {
//                throw new UnAuthorizedException(HttpStatus.UNAUTHORIZED, "Authorization header is missing");
//            }
//            return chain.filter(exchange);
//        };
    }

    public static class Config {
        private String name;
        public Config(String name){ this.name = name; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }

    @Override
    public Config newConfig() {
        return new Config("AuthenticationFilterFactory");
    }
    @Override
    public Class<Config> getConfigClass() {
        return null;
    }
}
*/
