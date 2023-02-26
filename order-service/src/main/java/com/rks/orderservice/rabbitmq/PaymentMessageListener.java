package com.rks.orderservice.rabbitmq;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PaymentMessageListener {

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "paymentservice-queue"),
                    exchange = @Exchange(
                    name = "paymentservice-exchange",
                    type = ExchangeTypes.TOPIC,
                    durable = "true",
                    autoDelete = "false",
                    arguments = {},
                    internal = "false",
                    ignoreDeclarationExceptions = "true"),
            key = "paymentservice-routing-key"))
    private void listenToPaymentQueue(byte[] message) {
        try {
            String msg = new String(message);
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            PaymentMessage paymentMessage = gson.fromJson(msg, PaymentMessage.class);
            log.info("Received a new message via {}. Message: {}", this.getClass().getName(), paymentMessage);
//            log.info("Processing payment for the new order received");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Exception occurred. Message {}", e.getMessage());
        }
    }
}
