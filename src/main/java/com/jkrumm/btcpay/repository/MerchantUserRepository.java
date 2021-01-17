package com.jkrumm.btcpay.repository;

import com.jkrumm.btcpay.domain.MerchantUser;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MerchantUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MerchantUserRepository extends JpaRepository<MerchantUser, Long> {}
