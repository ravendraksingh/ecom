package com.rks.dbservice.dbconfig;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@PropertySource({"classpath:application.properties"})
public class OrderDataSourceConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "spring.order-datasource")
    public DataSource orderDataSource() {
        return DataSourceBuilder.create().build();
    }
}
