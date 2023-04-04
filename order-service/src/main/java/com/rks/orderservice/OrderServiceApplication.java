package com.rks.orderservice;

//import com.rks.orderservice.config.DatabaseCreds;
//import com.rks.orderservice.config.OrderServiceConfig;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;

import org.springframework.core.env.Environment;

@SpringBootApplication(exclude = {
        RabbitAutoConfiguration.class,
//        DataSourceAutoConfiguration.class
})
public class OrderServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

    @Autowired
    public void setEnv(Environment e) {
        System.out.println("server.port: " + e.getProperty("server.port"));
    }

}
