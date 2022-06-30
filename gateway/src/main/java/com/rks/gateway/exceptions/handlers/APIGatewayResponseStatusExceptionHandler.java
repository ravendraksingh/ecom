package com.rks.gateway.exceptions.handlers;

import com.rks.gateway.exceptions.UnauthorizedAccessException;
import com.rks.mcommon.dto.response.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ResolvableType;
import org.springframework.core.annotation.Order;
import org.springframework.core.codec.Hints;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;
import reactor.core.publisher.Mono;

@Component
@Order(-2)
public class APIGatewayResponseStatusExceptionHandler extends ResponseStatusExceptionHandler {
    Logger logger = LoggerFactory.getLogger(APIGatewayResponseStatusExceptionHandler.class);

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        logger.info("in MyResponseStatusExceptionHandler.handle method");
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        if (ex instanceof UnauthorizedAccessException) {
            exchange.getResponse().setStatusCode(((UnauthorizedAccessException) ex).getStatus());
            ErrorResponse errorResponse = getErrorResponse((UnauthorizedAccessException) ex);

            return exchange.getResponse().writeWith(
                    new Jackson2JsonEncoder().encode(Mono.just(errorResponse), exchange.getResponse().bufferFactory(),
                    ResolvableType.forInstance(errorResponse), MediaType.APPLICATION_JSON,
                    Hints.from(Hints.LOG_PREFIX_HINT, exchange.getLogPrefix())));
        }
        return super.handle(exchange, ex);
    }

    private ErrorResponse getErrorResponse(ResponseStatusException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(ex.getRawStatusCode());
        errorResponse.setMessage(ex.getReason());
        errorResponse.setStatus("failed");
        return errorResponse;
    }
}
