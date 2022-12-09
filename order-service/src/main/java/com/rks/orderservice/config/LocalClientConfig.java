//package com.rks.orderservice.config;
//
//import lombok.Data;
//import lombok.extern.slf4j.Slf4j;
//import static com.rks.orderservice.config.AppConfiguration.addData;
//
//@Data
//@Slf4j
//public class LocalClientConfig {
//    private String clientId;
//    private String certificatePath;
//    private String certFileName;
//    private String certificatePassword;
//
//    public void init(OrderServiceConfig config) {
//        addData(config.getLocalClientConfig().getClientId(), "certificatePath", config.getLocalClientConfig().getCertificatePath());
//        addData(config.getLocalClientConfig().getClientId(), "certFileName", config.getLocalClientConfig().getCertFileName());
//        addData(config.getLocalClientConfig().getClientId(), "certificatePassword", config.getLocalClientConfig().getCertificatePassword());
//
//        log.info("clientId = " + config.getLocalClientConfig().getClientId());
//        log.info("certificatePath = " + config.getLocalClientConfig().getCertificatePath());
//        log.info("certFileName = " + config.getLocalClientConfig().getCertFileName());
//        log.info("certificatePassword = " + config.getLocalClientConfig().getCertificatePassword());
//    }
//}
