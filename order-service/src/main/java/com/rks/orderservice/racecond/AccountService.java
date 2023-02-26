package com.rks.orderservice.racecond;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import static org.apache.commons.lang3.compare.ComparableUtils.is;

@Slf4j
@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public BigDecimal getBalanceForAccount(Long acctNum) {
       Optional<Account> optionalAccount = accountRepository.findById(acctNum);
       if (!optionalAccount.isPresent()) {
           throw new RuntimeException("Account not present");
       }
       Account account = optionalAccount.get();
       return account.getBalance();
    }

    @Transactional
    public BigDecimal debitAccount(Long acctNum, BigDecimal debitAmt) {
        Optional<Account> optionalAccount = accountRepository.findById(acctNum);
        if (!optionalAccount.isPresent()) {
            throw new RuntimeException("Account not present");
        }
        Account account = optionalAccount.get();
        if (is(account.getBalance()).lessThan(debitAmt)) {
            throw new RuntimeException("Insufficient balance: debitAmt="+debitAmt+", balance="+account.getBalance());
        }
        log.info("debit b4 ac={}, amt={}, bal={}, ver={}", acctNum, debitAmt, account.getBalance(), account.getVersion());
        BigDecimal newBalance = account.getBalance().subtract(debitAmt);
        int newVersion  = account.getVersion() + 1;

        int recCount = accountRepository.updateAcctBalance(acctNum, newBalance, "debit", debitAmt, newVersion, account.getVersion());
        if (recCount != 1) {
            log.error("error: debit txn failed. ac={}, amt={}, bal={}", acctNum, debitAmt, account.getBalance());
            throw new RuntimeException("Could not perform txn");
        }

        int txnCount = accountRepository.createTxn(acctNum, "debit", debitAmt);
        log.info("debit after a/c={}, amt={}, bal={}, ver={}", acctNum, debitAmt, newBalance, newVersion);
        return newBalance;
    }

    @Transactional(rollbackFor = { RuntimeException.class })
    public BigDecimal creditAccount(Long acctNum, BigDecimal creditAmt) {
        Optional<Account> optionalAccount = accountRepository.findById(acctNum);
        if (!optionalAccount.isPresent()) {
            throw new RuntimeException("Account not present");
        }
        Account account = optionalAccount.get();

        log.info("credit b4 a/c={}, amt={}, bal={}, ver={}", acctNum, creditAmt, account.getBalance(), account.getVersion());

        BigDecimal newBalance = account.getBalance().add(creditAmt);
        int newVersion  = account.getVersion() + 1;

        int txnCount = accountRepository.createTxn(acctNum, "credit", creditAmt);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int recCount = accountRepository.updateAcctBalance(acctNum, newBalance, "credit", creditAmt, newVersion, account.getVersion());
        log.info("in credit recCount={}", recCount);

        if (recCount != 1) {
            log.error("error: credit txn failed. ac={}, amt={}, bal={}", acctNum, creditAmt, account.getBalance());
            throw new RuntimeException("Could not perform txn");
        }

        log.info("credit after a/c={}, amt={}, bal={}, ver={}", acctNum, creditAmt, newBalance, newVersion);
        return newBalance;
    }

    public Account create(Account account) {
        Optional<Account> optionalAccount = accountRepository.findById(account.getAccountNumber());
        if (optionalAccount.isPresent()) {
            throw new RuntimeException("Account already exists");
        }
        return accountRepository.save(account);
    }
}
