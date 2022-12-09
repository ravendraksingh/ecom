package com.rks.orderservice.search;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderSearchCriteria {
    private String key;
    private String operation;
    private Object value;
    private boolean orPredicate;

    public OrderSearchCriteria() {
    }

    public OrderSearchCriteria(String key, String operation, Object value) {
        super();
        this.key = key;
        this.operation = operation;
        this.value = value;
    }
}
