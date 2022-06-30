package com.rks.orderservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
//@PropertySource("classpath:application.properties")
@ConditionalOnProperty(name = "order-created.exchange.name", havingValue = "order-created-exchange")
public class OrderCreatedRabbitMQProperties {

    @Value("${order-created.exchange.name}")
    private String orderCreatedExchangeName;

    @Value("${order-created.queue.name}")
    private String orderCreatedQueueName;

    @Value("${order-created.routing.key}")
    private String orderCreatedRoutingKey;


}
