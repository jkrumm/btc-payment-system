package com.jkrumm.btcpay.repository;

import com.jkrumm.btcpay.domain.Confidence;
import com.jkrumm.btcpay.domain.Transaction;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Confidence entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConfidenceRepository extends JpaRepository<Confidence, Long> {
    Confidence findFirstByTransactionOrderByChangeAt(Transaction tx);

    List<Confidence> findByTransactionOrderByChangeAtDesc(Transaction tx);
}
