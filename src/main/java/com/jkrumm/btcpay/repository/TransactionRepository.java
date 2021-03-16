package com.jkrumm.btcpay.repository;

import com.jkrumm.btcpay.domain.Transaction;
import com.jkrumm.btcpay.domain.User;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Transaction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("select transaction from Transaction transaction where transaction.user.login = ?#{principal.username}")
    List<Transaction> findByUserIsCurrentUser();

    List<Transaction> findByUserIsIn(List<User> users);

    List<Transaction> findByAddress(String address);

    Transaction findTopByTxHash(String txHash);

    Transaction findTopByOrderByIdDesc();
}
