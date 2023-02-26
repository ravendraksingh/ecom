package com.rks.orderservice.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnProperty(name = "orderservice.exchange.name", havingValue = "orderservice-exchange")
public class OrderCreatedMessageProducer {
    private RabbitTemplate template;
    private OrderServiceRabbitMQProperties properties;


    public OrderCreatedMessageProducer(RabbitTemplate template, OrderServiceRabbitMQProperties properties) {
        this.template = template;
        this.properties = properties;
    }

    public void sendMessage(OrderMessage message) {
        log.info("Sending message to queue via {}", this.getClass().getName());
        try {
            log.debug("Order message is {}", message);
            template.convertAndSend(properties.getOrderserviceExchangeName(), properties.getOrderserviceRoutingKey(),
                    message);
        } catch (Exception e) {
            log.error("Exception occurred. Message {}", e.getMessage());
        }

    }
}
