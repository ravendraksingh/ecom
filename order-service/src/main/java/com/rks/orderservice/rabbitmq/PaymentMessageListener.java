package com.rks.orderservice.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rks.orderservice.entity.Order;
import com.rks.orderservice.repository.OrderRepository;
import com.rks.orderservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class PaymentMessageListener {
    @Autowired
    private OrderRepository orderRepository;

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
            ObjectMapper objectMapper = new ObjectMapper();
            PaymentMessage paymentMessage = objectMapper.readValue(message, PaymentMessage.class);
            log.info("Received a new message via {}. Message: {}", this.getClass().getName(), paymentMessage);
            log.info("Updating order details with payment info");
            Optional<Order> optionalOrder = orderRepository.findById(Long.parseLong(paymentMessage.getOrderid()));
            if (!optionalOrder.isPresent()) {
                log.error("Order not present for order id {}", paymentMessage.getOrderid());
                throw new RuntimeException("Order not present!!!");
            }
            Order order = optionalOrder.get();
            order.setPaymentStatus("paid");
            order.setPaymentDate(paymentMessage.getTransaction_date());
            orderRepository.save(order);
            log.info("Successfully updated payment info for order id: {}", order.getId());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Exception occurred. Message {}", e.getMessage());
        }
    }
}
