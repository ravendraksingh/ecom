//package com.rks.orderservice.config;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Slf4j
//@Configuration
//public class AppConfiguration implements InitializingBean {
//
////    @Autowired
////    private OrderServiceConfig orderServiceConfig;
//
//    public static final Map<String, String> data = new HashMap<>();
//
//    public static void addData(String clientId, String property, String value) {
//        data.put(key(clientId, property), value);
//    }
//    private static String key(String clientId, String property) {
//        return clientId.toLowerCase() + "-" + property.toLowerCase();
//    }
//    public static String lookupVal(String clientId, String property) {
//        return data.get(key(clientId, property));
//    }
//
////    @Override
////    public void afterPropertiesSet() throws Exception {
////        orderServiceConfig.getLocalClientConfig().init(orderServiceConfig);
////    }
//}
