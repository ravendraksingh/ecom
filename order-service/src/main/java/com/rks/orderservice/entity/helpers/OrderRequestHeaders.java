package com.rks.orderservice.entity.helpers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderRequestHeaders {
    private String traceId;
    private String timestamp;
    private String clientId;
    private String clientSecret;
}
