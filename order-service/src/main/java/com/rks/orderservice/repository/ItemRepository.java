package com.rks.orderservice.repository;

import com.rks.orderservice.entity.Item;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ItemRepository extends PagingAndSortingRepository<Item, Long> {

}
