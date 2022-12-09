package com.rks.orderservice.search;

import com.rks.orderservice.entity.Order;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderSearchSpecBuilder {

    private final List<OrderSearchCriteria> params;

    public OrderSearchSpecBuilder() {
        this.params = new ArrayList<>();
    }

    public OrderSearchSpecBuilder with(String key, String operation, Object value) {
        params.add(new OrderSearchCriteria(key, operation, value));
        return this;
    }

    public Specification<Order> build() {
        if (params.size() == 0) {
            return null;
        }
        List<Specification> specs = params.stream()
                .map(OrderSpecification::new)
                .collect(Collectors.toList());

        Specification result = specs.get(0);

        for (int i = 1; i < params.size(); i++) {
            result = params.get(i)
                    .isOrPredicate()
                    ? Specification.where(result)
                    .or(specs.get(i))
                    : Specification.where(result)
                    .and(specs.get(i));
        }
        return result;
    }
}
