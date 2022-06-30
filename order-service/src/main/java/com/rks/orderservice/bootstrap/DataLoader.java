package com.rks.orderservice.bootstrap;

import com.rks.orderservice.domain.Item;
import com.rks.orderservice.domain.Order;
import com.rks.orderservice.exceptions.ServiceError;
import com.rks.orderservice.exceptions.ServiceErrorRepository;
import com.rks.orderservice.repository.OrderRepository;
import com.rks.orderservice.util.StatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;


@Component
public class DataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ServiceErrorRepository errorRepository;

    @Override
    public void run(String... args) throws Exception {
        //orderRepository.deleteAll();
        long count = orderRepository.count();
        log.info("Existing order count in table: " + count);
        if (count == 0) {
            loadData();
        }
        //testUpdateOrder();
        log.info("Loading new exceptions into table");
        //loadExceptions();
    }

    private void loadExceptions() {
        ServiceError err1 = new ServiceError();
        err1.setError_code("ORNFE0001");
        err1.setError_message("Order not found");
        err1.setException_name("OrderNotFound");
        err1.setResponse_code("ORNFE0001");
        err1.setService_name("OR");
        err1.setStatus("failure");
        err1.setDescription("ORDER_NOT_FOUND");
        errorRepository.save(err1);
    }


    private void loadData() {
        log.info("Loading data ...........");

        Order myOrder = new Order();
        myOrder.setOrderStatus(StatusEnum.ORDER_CREATED.getStatus());
        myOrder.setOrderDate(new Date());
        myOrder.setUserEmail("ravendraksingh@gmail.com");

        Item myItem = new Item();
        myItem.setName("Trouser");
        myItem.setQuantity(5);
        myItem.setPrice(new BigDecimal(2000));
        myItem.setOrder(myOrder);
        myOrder.getItems().add(myItem);

        Item myItem2 = new Item();
        myItem2.setName("Clock");
        myItem2.setQuantity(1);
        myItem2.setPrice(new BigDecimal(300));
        myItem2.setOrder(myOrder);
        myOrder.getItems().add(myItem2);

        orderRepository.save(myOrder);
    }
}
