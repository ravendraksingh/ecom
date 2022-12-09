package com.rks.orderservice.search;

import com.rks.orderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderSearchRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
}
