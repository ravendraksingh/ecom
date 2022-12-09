package com.rks.paymentservice.repository;

import com.rks.paymentservice.entity.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Long> {

    List<Transaction> findAllByEmailOrderByTransactionidDesc(String email);

}
