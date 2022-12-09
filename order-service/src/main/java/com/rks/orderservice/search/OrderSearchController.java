package com.rks.orderservice.search;

import com.rks.orderservice.dto.response.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class OrderSearchController {

    private OrderSearchService searchService;
    @Autowired
    public OrderSearchController(OrderSearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/orders/search")
    public List<OrderResponse> search3(@RequestParam(value = "filter") String search) {
        return searchService.search2(search);
    }
}
