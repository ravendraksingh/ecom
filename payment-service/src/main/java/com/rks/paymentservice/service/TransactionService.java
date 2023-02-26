package com.rks.paymentservice.service;

import com.rks.paymentservice.dto.request.TransactionRequest;
import com.rks.paymentservice.dto.response.TransactionResponse;
import com.rks.paymentservice.entity.Transaction;
import com.rks.paymentservice.rabbitmq.OrderMessage;
import com.rks.paymentservice.rabbitmq.PaymentMessage;
import com.rks.paymentservice.rabbitmq.PaymentMessageProducer;
import com.rks.paymentservice.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private PaymentMessageProducer paymentMessageProducer;

    @Value("${rabbitmq.enabled}")
    private boolean queueEnabled;

    public TransactionResponse create(TransactionRequest request) {
        log.info("Creating a new txn for, request=" + request);
        Transaction tx = createTxnForTxnRequest(request);
        Transaction savedTxn = transactionRepository.save(tx);
        TransactionResponse transactionResponse = createTxnResponseForTxn(savedTxn);
        log.info("response = " + transactionResponse);
        if (queueEnabled) {
            log.info("Queue is enabled");
            PaymentMessage paymentMessage = createPaymentMessageForTransaction(savedTxn);
            log.info("Sending paymentMessage {} to message queue", paymentMessage);
            paymentMessageProducer.sendMessage(paymentMessage);
            log.info("Message successfully sent to queue");
        }
        return transactionResponse;
    }

     public TransactionResponse getById(Long transactionId) {
        Optional<Transaction> optionalTxn = transactionRepository.findById(transactionId);
        if (!optionalTxn.isPresent()) {
            throw new RuntimeException("Transaction id " + transactionId + " not found");
        }
        TransactionResponse response = createTxnResponseForTxn(optionalTxn.get());
        return response;
    }

    public TransactionResponse getByPGOrderId(Long pgOrderId) {
        Transaction optionalTxn = transactionRepository.findByPgorderid(pgOrderId);
        if (optionalTxn == null) {
            throw new RuntimeException("Transaction not found for PG Order Id: " + pgOrderId );
        }
        TransactionResponse response = createTxnResponseForTxn(optionalTxn);
        return response;
    }

    public List<TransactionResponse> getAllByEmail(String email) {
        log.info("Fetching all txns for email={}", email);
        List<Transaction> txnList = transactionRepository.findAllByEmailOrderByTransactionidDesc(email);
        log.info("txnList={}", txnList);
        //return orders.stream().map(this::createOrderResponseForOrder).collect(Collectors.toList());
        return txnList.stream().map(this::createTxnResponseForTxn).collect(Collectors.toList());
    }

    private Transaction createTxnForTxnRequest(TransactionRequest request) {
        Transaction txn = new Transaction();
        txn.setPgorderid(request.getPgorderid());
        txn.setOrderid(request.getOrderid());
        txn.setEntity("transaction");
        txn.setMercid(request.getMercid());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        txn.setTransaction_date(timestamp);
        txn.setAmount(request.getAmount());
        txn.setSurcharge(BigDecimal.ZERO);
        txn.setDiscount(BigDecimal.ZERO);
        txn.setCharge_amount(request.getAmount());
        txn.setCurrency(request.getCurrency());
        txn.setRu(request.getRu());
        txn.setCustomer(request.getCustomer());
        txn.setDevice(request.getDevice());
        txn.setStatus("created");
//        txn.setBankid();
        txn.setItemcode(request.getItemcode());
        txn.setSettlement_lob(request.getSettlement_lob());
        txn.setCard(request.getCard());
        txn.setNext_step(request.getNext_step());
        txn.setCreated_at(timestamp);
        txn.setUpdated_at(timestamp);
        txn.setValid_till(timestamp);
        txn.setEmail(request.getEmail());
        return txn;
    }

    private TransactionResponse createTxnResponseForTxn(Transaction transaction) {
        TransactionResponse response = new TransactionResponse();
        response.setEntity(transaction.getEntity());
        response.setTransactionid(transaction.getTransactionid());
        response.setPgorderid(transaction.getPgorderid());
        response.setOrderid(transaction.getOrderid());
        response.setMercid(transaction.getMercid());
        response.setTransaction_date(transaction.getTransaction_date());
        response.setAmount(transaction.getAmount());
        response.setSurcharge(transaction.getSurcharge());
        response.setDiscount(transaction.getDiscount());
        response.setCharge_amount(transaction.getCharge_amount());
        response.setCurrency(transaction.getCurrency());
        response.setRu(transaction.getRu());
        response.setStatus(transaction.getStatus());
        response.setCustomer(transaction.getCustomer());
        response.setBankid(transaction.getBankid());
        response.setItemcode(transaction.getItemcode());
        response.setSettlement_lob(transaction.getSettlement_lob());
        response.setBank_ref_no(transaction.getBank_ref_no());
        response.setAuth_status(transaction.getAuth_status());
        response.setTxn_process_type(transaction.getTxn_process_type());
        response.setTransaction_error_code(transaction.getTransaction_error_code());
        response.setTransaction_error_desc(transaction.getTransaction_error_desc());
        response.setTransaction_error_type(transaction.getTransaction_error_type());
        response.setAuthcode(transaction.getAuthcode());
        response.setNetwork_transactiionid(transaction.getNetwork_transactiionid());
        response.setEci(transaction.getEci());
        response.setPayment_method_type(transaction.getPayment_method_type());
        response.setNext_step(transaction.getNext_step());
        response.setValid_till(transaction.getValid_till());
        response.setCreated_at(transaction.getCreated_at());
        //response.setUpdated_at(transaction.getUpdated_at());
        response.setEmail(transaction.getEmail());
        return response;
    }

    private PaymentMessage createPaymentMessageForTransaction(Transaction transaction) {
        PaymentMessage message = new PaymentMessage();
        message.setTransactionid(transaction.getTransactionid());
        message.setOrderid(transaction.getOrderid());
        message.setMercid(transaction.getMercid());
        message.setTransaction_date(transaction.getTransaction_date());
        message.setSurcharge(transaction.getSurcharge());
        message.setDiscount(transaction.getDiscount());
        message.setNet_amount(transaction.getAmount());
        return message;
    }


}
