package com.rks.gateway.exceptions.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.codec.support.DefaultServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;

//@Component
//@Order(-2)
public class GlobalErrorWebExceptionHandler extends AbstractErrorWebExceptionHandler {
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    public GlobalErrorWebExceptionHandler(ErrorAttributes errorAttributes, WebProperties.Resources resources, ApplicationContext applicationContext) {
        super(errorAttributes, resources, applicationContext);
        ServerCodecConfigurer configurer = new DefaultServerCodecConfigurer();
        this.setMessageWriters(configurer.getWriters());
        this.setMessageReaders(configurer.getReaders());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        LOGGER.info("in GlobalErrorWebExceptionHandler:getRoutingFunction");
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
        //return null;
    }

    private Mono<ServerResponse> renderErrorResponse(final ServerRequest request) {
        ErrorAttributeOptions options = ErrorAttributeOptions
                .defaults()
                .including(ErrorAttributeOptions.Include.BINDING_ERRORS)
                .including(ErrorAttributeOptions.Include.MESSAGE)
                .including(ErrorAttributeOptions.Include.STACK_TRACE);

        final Map<String, Object> errorPropertiesMap = getErrorAttributes(request, options);

        //print stack trace before removing from response
        LOGGER.error("--*--*-- errorPropertiesMap : starts --*--*--");
        errorPropertiesMap.keySet().forEach(key -> {
            LOGGER.error(key + ":" + errorPropertiesMap.get(key));
        });
        LOGGER.error("--*--*-- errorPropertiesMap : ends --*--*--");

        //LOGGER.error((String)errorPropertiesMap.get("trace"));
        updateErrorPropertiesMap(errorPropertiesMap);

//        return ServerResponse.status(HttpStatus.BAD_REQUEST)
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(BodyInserters.fromValue(errorPropertiesMap));

        return ServerResponse.status((Integer) errorPropertiesMap.get("status"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(errorPropertiesMap));

    }

    private void updateErrorPropertiesMap(Map<String, Object> errorPropertiesMap) {
        errorPropertiesMap.remove("trace");
        errorPropertiesMap.remove("timestamp");
        errorPropertiesMap.remove("path");
        errorPropertiesMap.remove("exception");
        errorPropertiesMap.remove("requestId");
    }
}
