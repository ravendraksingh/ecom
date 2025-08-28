package com.niyava.ecom.orderservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotNull;

@Data
@ConfigurationProperties(prefix = "mysql")
@Configuration
@Validated
public class MySQLConfig {
    @NotNull
    private  String username;
    @NotNull
    private String password;
    @NotNull
    private String url;
}
