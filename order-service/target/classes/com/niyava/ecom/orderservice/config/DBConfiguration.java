package com.niyava.ecom.orderservice.config;

import com.niyava.encryption.RKSEncryption;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DBConfiguration {
    @Autowired
    private MySQLConfig mySQLConfig;

    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setUsername(mySQLConfig.getUsername());
//        dataSource.setPassword(mySQLConfig.getPassword());
        dataSource.setPassword(RKSEncryption.decrypt(mySQLConfig.getPassword()));
        dataSource.setJdbcUrl(mySQLConfig.getUrl());
        dataSource.setPoolName("order-service db pool");
        return dataSource;
    }
}
