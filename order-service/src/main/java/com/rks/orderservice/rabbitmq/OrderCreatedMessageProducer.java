package com.rks.orderservice.rabbitmq;

import com.rks.orderservice.config.OrderCreatedRabbitMQProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnProperty(name = "order-service.exchange.name", havingValue = "order-service-exchange")
public class OrderCreatedMessageProducer {
    private RabbitTemplate template;
    private OrderCreatedRabbitMQProperties properties;

    public OrderCreatedMessageProducer(RabbitTemplate template, OrderCreatedRabbitMQProperties properties) {
        this.template = template;
        this.properties = properties;
    }

    public void sendMessage(OrderMessage message) {
        log.info("Sending message to kafka thru {}", this.getClass().getName());
        try {
            log.debug("Order message is {}", message);
            template.convertAndSend(properties.getOrderCreatedExchangeName(), properties.getOrderCreatedRoutingKey(),
                    message);
        } catch (Exception e) {
            log.error("Exception occurred. Message {}", e.getMessage());
        }

    }
}
