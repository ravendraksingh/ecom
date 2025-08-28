package com.niyava.ecom.orderservice.repository;

import com.niyava.ecom.orderservice.entity.Item;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ItemRepository extends PagingAndSortingRepository<Item, Long> {

}
