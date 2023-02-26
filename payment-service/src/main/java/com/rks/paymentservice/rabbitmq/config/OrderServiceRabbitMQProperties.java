package com.rks.paymentservice.rabbitmq.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Setter
@Getter
@Configuration
@PropertySource("classpath:application.properties")
public class OrderServiceRabbitMQProperties {

    @Value("${orderservice.exchange.name}")
    private String orderserviceExchangeName;

    @Value("${orderservice.queue.name}")
    private String orderserviceQueueName;

    @Value("${orderservice.routing.key}")
    private String orderserviceRoutingKey;
}
