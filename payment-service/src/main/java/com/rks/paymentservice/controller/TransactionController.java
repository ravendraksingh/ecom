package com.rks.paymentservice.controller;

import com.rks.paymentservice.dto.request.TransactionRequest;
import com.rks.paymentservice.dto.response.TransactionResponse;
import com.rks.paymentservice.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
//@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080", "http://localhost:9080", "http://localhost:9090"})
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/users/{email}/txns")
    public List<TransactionResponse> getAllByEmail(@PathVariable String email) throws InterruptedException {
        return transactionService.getAllByEmail(email);
    }


    @PostMapping("/transactions")
    public TransactionResponse create(@RequestBody TransactionRequest request) {
        return transactionService.create(request);
    }

    @GetMapping("/transactions/{transactionId}")
    public TransactionResponse getById(@PathVariable Long transactionId) {
        return transactionService.getById(transactionId);
    }
}
