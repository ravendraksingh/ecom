package com.rks.paymentservice.rabbitmq;//package com.rks.paymentservice.rabbitmq;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rks.paymentservice.dto.response.TransactionResponse;
import com.rks.paymentservice.entity.Transaction;
import com.rks.paymentservice.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderServiceListener {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private PaymentMessageProducer paymentMessageProducer;

    @Value("${rabbitmq.enabled}")
    private boolean queueEnabled;

    @Autowired
    public OrderServiceListener() {
    }

    @RabbitListener(queues = "${orderservice.queue.name}")
    public void listenToOrderCreated(byte[] message) {
        try {
            String msg = new String(message);
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            OrderMessage orderMessage = gson.fromJson(msg, OrderMessage.class);
            log.info("Received a new message via {}. Message: {}", this.getClass().getName(), orderMessage);

            if (queueEnabled) {
                PaymentMessage paymentMessage = updatePaymentDetailsAndCreatePaymentMessage(orderMessage);
                log.info("Queue is enabled, Sending PaymentMessage to message queue");
                paymentMessageProducer.sendMessage(paymentMessage);
                log.info("Message successfully sent to queue");
            }
        } catch (Exception e) {
            log.error("Exception occurred. Message {}", e.getMessage());
        }
    }

    private PaymentMessage updatePaymentDetailsAndCreatePaymentMessage(OrderMessage message) {
        //TransactionResponse transaction = transactionService.getByPGOrderId(message.getOrder_id());
        Transaction transaction = transactionRepository.findByPgorderid(message.getOrder_id());
        PaymentMessage paymentMessage = new PaymentMessage();
        paymentMessage.setTransactionid(transaction.getTransactionid());
        paymentMessage.setOrderid(message.getOrder_id().toString());
        paymentMessage.setTransaction_date(transaction.getTransaction_date());
        paymentMessage.setSurcharge(transaction.getSurcharge());
        paymentMessage.setDiscount(transaction.getDiscount());
        paymentMessage.setNet_amount(transaction.getAmount());
        return paymentMessage;
    }
}
