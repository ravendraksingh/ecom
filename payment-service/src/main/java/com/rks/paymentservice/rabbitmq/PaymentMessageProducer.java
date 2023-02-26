package com.rks.paymentservice.rabbitmq;

import com.rks.paymentservice.rabbitmq.PaymentMessage;
import com.rks.paymentservice.rabbitmq.config.PaymentServiceRabbitMQProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnProperty(name = "paymentservice.exchange.name", havingValue = "paymentservice-exchange")
public class PaymentMessageProducer {
    private RabbitTemplate template;
    private PaymentServiceRabbitMQProperties properties;

    public PaymentMessageProducer(RabbitTemplate template, PaymentServiceRabbitMQProperties properties) {
        this.template = template;
        this.properties = properties;
    }

    public void sendMessage(PaymentMessage message) {
        log.info("Sending message to queue via {}", this.getClass().getName());
        try {
            log.debug("Order message is {}", message);
            template.convertAndSend(properties.getPaymentserviceExchangeName(), properties.getPaymentserviceRoutingKey(),
                    message);
        } catch (Exception e) {
            log.error("Exception occurred. Message {}", e.getMessage());
        }

    }
}
