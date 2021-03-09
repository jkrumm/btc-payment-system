package com.jkrumm.btcpay.repository;

import com.jkrumm.btcpay.domain.Confidence;
import com.jkrumm.btcpay.domain.Transaction;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Confidence entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConfidenceRepository extends JpaRepository<Confidence, Long> {
    Confidence findFirstByTransactionOrderByChangeAt(Transaction tx);
}
