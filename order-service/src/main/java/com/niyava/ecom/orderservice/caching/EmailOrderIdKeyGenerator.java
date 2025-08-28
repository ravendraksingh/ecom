package com.niyava.ecom.orderservice.caching;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Component
public class EmailOrderIdKeyGenerator implements KeyGenerator {
    @Override
    public Object generate(Object target, Method method, Object... params) {
        log.info("params: " + params[0] + " // " + params[1]);
        return "order-" + params[0] + "-" + params[1];
    }
}
