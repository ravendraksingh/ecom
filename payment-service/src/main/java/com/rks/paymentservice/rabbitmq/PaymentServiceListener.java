//package com.rks.paymentservice.rabbitmq;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.rks.paymentservice.service.PaymentService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class PaymentServiceListener {
//    private static final Logger log = LoggerFactory.getLogger(PaymentServiceListener.class);
//
//    @Autowired
//    public PaymentServiceListener() {
//    }
//
//    @RabbitListener(queues = "${rabbitmq.queueName}")
//    public void listenToPaymentCreated(byte[] message) {
//
//        String msg = new String(message);
//        Gson gson = new GsonBuilder().create();
//        PaymentMessage paymentMessage = gson.fromJson(msg, PaymentMessage.class);
//        log.info("Received a new message via {}. Message: {}", this.getClass().getName(), paymentMessage);
//
//        log.info("Processing payment for the new order received");
//        try {
//            Thread.sleep(10000);
//            //processPaymentForNewOrder(orderMessage);
//            doPaymentForNewOrder(orderMessage);
//        } catch (Exception e) {
//            log.error("Exception occurred. Message {}", e.getMessage());
//        }
//
//    }
//
//    private void doPaymentForNewOrder(OrderMessage orderMessage) {
//        Long orderId = orderMessage.getOrderId();
//        paymentService.createPaymentForNewOrder(orderId);
//    }
//
//    /*private void processPaymentForNewOrder(OrderMessage message) {
//        Order newOrder = orderRepository.findById(message.getOrderId()).get();
//        if (newOrder == null) {
//            throw new RuntimeException("Order id is null in the new order");
//        }
//        newOrder.setOrderStatus(StatusEnum.ORDER_PAID.getStatus());
//        orderRepository.save(newOrder);
//    }*/
//}
