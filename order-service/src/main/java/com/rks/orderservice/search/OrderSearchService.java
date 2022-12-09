package com.rks.orderservice.search;

import com.rks.orderservice.entity.Order;
import com.rks.orderservice.dto.request.OrderRequest;
import com.rks.orderservice.dto.response.OrderResponse;
import com.rks.orderservice.mappers.OrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class OrderSearchService {
    Logger logger = LoggerFactory.getLogger(OrderSearchService.class);

    @Autowired
    private OrderMapper orderMapper;
    private OrderSearchRepository repository;

    @Autowired
    public OrderSearchService(OrderSearchRepository repository) {
        this.repository = repository;
    }

    public List<OrderResponse> search(String search) {
        OrderSpecification s1 = new OrderSpecification(new OrderSearchCriteria("userEmail", ":", "ravendraksingh@gmail.com"));
        OrderSpecification s2 = new OrderSpecification(new OrderSearchCriteria("id", ":", "139"));
        //OrderSpecification s2 = new OrderSpecification(new OrderSearchCriteria())

        List<Order> orders = repository.findAll(Specification.where(s1).and(s2));
        List<OrderResponse> orderResponseList = orders.stream().map(this::createOrderResponseForOrder).collect(Collectors.toList());
        return orderResponseList;
    }

    public List<OrderResponse> search2(String filter) {
        System.out.println("filter: " + filter);
        OrderSearchSpecBuilder builder = new OrderSearchSpecBuilder();

        //Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
        Pattern pattern = Pattern.compile("([\\w+?])(:|<|>)(.*),");
        System.out.println("pattern: " + pattern);

        Matcher matcher = pattern.matcher(filter + ",");
        System.out.println("matcher: " + matcher);

        int i = 1;
        while (matcher.find()) {
            System.out.println("match found. i= " + i++);
            System.out.println(builder.with(matcher.group(1), matcher.group(2), matcher.group(3)));
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }
        Specification<Order> spec = builder.build();
        System.out.println("spec: " + spec);
        List<Order> orders = repository.findAll(spec);
        return orders.stream().map(this::createOrderResponseForOrder).collect(Collectors.toList());
    }

    public List<OrderResponse> search3(String filter) {
        System.out.println("filter: " + filter);

        String[] filters = filter.split(",");
        Arrays.stream(filters).forEach(System.out::print);

        //System.out.println("spec: " + spec);
        //List<Order> orders = repository.findAll(spec);
        //return orders.stream().map(this::createOrderResponseForOrder).collect(Collectors.toList());
        return null;
    }

    public OrderResponse createOrderResponseForOrder(Order order) {
        OrderResponse orderResponse = new OrderResponse();
        orderMapper.map(order, orderResponse);
        return orderResponse;
    }

    private Order createOrderForOrderRequest(OrderRequest orderRequest) {
        Order order = new Order();
        orderMapper.map(orderRequest, order);
        return order;
    }
}
