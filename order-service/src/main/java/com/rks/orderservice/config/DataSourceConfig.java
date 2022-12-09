package com.rks.orderservice.config;

import com.rks.encryption.RKSEncryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.AbstractDataSource;

import javax.sql.DataSource;

//@Configuration
//public class DataSourceConfig {
//    @Autowired
//    private DatabaseCreds databaseCreds;
//
//    @Bean
//    public DataSource dataSource() throws Exception {
//        return DataSourceBuilder.create()
//                .username(databaseCreds.getUsername())
//                .password(RKSEncryption.decrypt(databaseCreds.getPassword()))
//                .url(databaseCreds.getUrl())
//                .build();
//    }
//}
