package com.jkrumm.btcpay.repository;

import com.jkrumm.btcpay.domain.Transaction;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Transaction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("select transaction from Transaction transaction where transaction.user.login = ?#{principal.username}")
    List<Transaction> findByUserIsCurrentUser();
}
