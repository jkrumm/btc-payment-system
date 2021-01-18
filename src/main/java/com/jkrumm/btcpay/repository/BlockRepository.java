package com.jkrumm.btcpay.repository;

import com.jkrumm.btcpay.domain.Block;
import java.util.stream.DoubleStream;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Block entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BlockRepository extends JpaRepository<Block, Long> {
    Block findTopByOrderByIdDesc();
}
