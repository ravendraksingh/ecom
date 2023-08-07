package com.rks.orderservice.caching;


import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;

public class EmailOrdersKeyGenerator implements KeyGenerator {
    @Override
    public Object generate(Object target, Method method, Object... params) {
        return params[1];
    }
}
