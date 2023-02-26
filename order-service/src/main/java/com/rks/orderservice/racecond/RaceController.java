package com.rks.orderservice.racecond;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1")
public class RaceController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/accounts")
    public Account create(@RequestBody Account account) {
        return accountService.create(account);
    }

    @GetMapping("/accounts/{acctNum}/balance")
    public String getBalance(@PathVariable Long acctNum) {
        return accountService.getBalanceForAccount(acctNum).toString();
    }

    @GetMapping("/accounts/{acctNum}/debit/{amount}")
    public String debitAccount(@PathVariable Long acctNum, @PathVariable BigDecimal amount) {
        return accountService.debitAccount(acctNum, amount).toString();
    }

    @GetMapping("/accounts/{acctNum}/credit/{amount}")
    public String creditAccount(@PathVariable Long acctNum, @PathVariable BigDecimal amount) {
        return accountService.creditAccount(acctNum, amount).toString();
    }
}
