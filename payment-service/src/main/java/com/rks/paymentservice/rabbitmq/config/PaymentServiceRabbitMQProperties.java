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
public class PaymentServiceRabbitMQProperties {

    @Value("${paymentservice.exchange.name}")
    private String paymentserviceExchangeName;

    @Value("${paymentservice.queue.name}")
    private String paymentserviceQueueName;

    @Value("${paymentservice.routing.key}")
    private String paymentserviceRoutingKey;


}
