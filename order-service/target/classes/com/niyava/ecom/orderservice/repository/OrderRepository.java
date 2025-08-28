package com.niyava.ecom.orderservice.repository;

import com.niyava.ecom.orderservice.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order, Long>, CrudRepository<Order, Long> {

    @Query("from orders t where t.orderStatus = :orderStatus")
    List<Order> findByOrderStatus(@Param("orderStatus") String orderStatus);
    List<Order> findAllByUserEmailOrderByIdDesc(String email);

    Order findOrderByUserEmailAndId(String email, Long id);

    @Query("from orders t where t.userEmail = :email and t.orderDate = :date")
    List<Order> findAllOrdersByUserEmailAndOrderDate(@Param("email") String email, @Param("date") LocalDate date);

    Page<Order> findAllByUserEmail(String email, Pageable pageRequest);
}
