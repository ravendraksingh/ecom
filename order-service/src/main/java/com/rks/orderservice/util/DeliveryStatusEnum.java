package com.rks.orderservice.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DeliveryStatusEnum {
    PENDING("pending"),
    DELIVERED("delivered"),
    RETURNED("returned"),
    FAILED("failed")
    ;
    private String deliveryStatus;
}
