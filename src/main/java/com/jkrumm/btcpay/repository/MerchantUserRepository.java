package com.jkrumm.btcpay.repository;

import com.jkrumm.btcpay.domain.MerchantUser;
import com.jkrumm.btcpay.domain.User;
import com.jkrumm.btcpay.service.dto.MerchantDTO;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the MerchantUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MerchantUserRepository extends JpaRepository<MerchantUser, Long> {
    MerchantUser findMerchantUserByUser(Optional<User> user);
}
