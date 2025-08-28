package com.niyava.ecom.orderservice.racecond;

import org.springframework.stereotype.Component;

@Component
public class InsufficientBalanceException extends RuntimeException {

    public InsufficientBalanceException() {
        super();
    }
    public InsufficientBalanceException(String message) {
        super(message);
    }
}
