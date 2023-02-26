package com.rks.orderservice.rabbitmq;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
//@PropertySource("classpath:application.yml")
@ConditionalOnProperty(name = "orderservice.exchange.name", havingValue = "orderservice-exchange")
public class OrderServiceRabbitMQProperties {

    @Value("${orderservice.exchange.name}")
    private String orderserviceExchangeName;

    @Value("${orderservice.queue.name}")
    private String orderserviceQueueName;

    @Value("${orderservice.routing.key}")
    private String orderserviceRoutingKey;


}
