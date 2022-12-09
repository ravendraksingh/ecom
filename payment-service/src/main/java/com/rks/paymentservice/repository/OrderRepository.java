package com.rks.paymentservice.repository;

import com.rks.paymentservice.entity.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
