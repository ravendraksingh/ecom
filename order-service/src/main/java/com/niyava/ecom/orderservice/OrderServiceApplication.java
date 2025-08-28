package com.niyava.ecom.orderservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.core.env.Environment;

/**
 * Ravendra Singh: 21-Nov-2023 12:01 pm
 * spring.autoconfigure.exclude property added in the application-dev.properties file and removed from
 * SpringBootApplication annotation
 */
@EnableCaching
//@SpringBootApplication(exclude = {
//        RabbitAutoConfiguration.class,
//        DataSourceAutoConfiguration.class
//})
@SpringBootApplication
public class OrderServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

    @Autowired
    public void setEnv(Environment e) {
        System.out.println("server.port: " + e.getProperty("server.port"));
    }

}
