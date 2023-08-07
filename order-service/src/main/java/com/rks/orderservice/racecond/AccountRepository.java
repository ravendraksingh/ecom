package com.rks.orderservice.racecond;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "update accounts set balance = :newBalance, version = :newVersion, " +
            "last_txn_type = :txnType, last_txn_amt = :txnAmt " +
            "where account_number = :acctNum " +
            "and version = :oldVersion", nativeQuery = true)
    int updateAcctBalance(@Param("acctNum") Long acctNum, @Param("newBalance") BigDecimal newBalance,
                          @Param("txnType") String txnType, @Param("txnAmt") BigDecimal txnAmt,
                          @Param("newVersion") int newVersion,
                          @Param("oldVersion") int oldVersion);


    @Modifying
    @Query(value = "insert into transactions (account_number, txn_type, txn_amt) values " +
            "( :acctNum, :txnType, :txnAmt )",
            nativeQuery = true)
    int createTxn(@Param("acctNum") Long acctNum, @Param("txnType") String tnxType,
              @Param("txnAmt") BigDecimal txnAmt);

    @Query(value = "SELECT @@global.transaction_ISOLATION", nativeQuery = true)
    String getIsolationLevel();

    @Query(value = "SELECT @@session.transaction_ISOLATION", nativeQuery = true)
    String getSessionIsolationLevel();

    @Query(value = "SELECT @@session.autocommit", nativeQuery = true)
    String getSessionAutoCommit();
}
