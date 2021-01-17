package com.jkrumm.btcpay.service;

import com.jkrumm.btcpay.service.dto.MerchantUserDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.jkrumm.btcpay.domain.MerchantUser}.
 */
public interface MerchantUserService {
    /**
     * Save a merchantUser.
     *
     * @param merchantUserDTO the entity to save.
     * @return the persisted entity.
     */
    MerchantUserDTO save(MerchantUserDTO merchantUserDTO);

    /**
     * Get all the merchantUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MerchantUserDTO> findAll(Pageable pageable);

    /**
     * Get the "id" merchantUser.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MerchantUserDTO> findOne(Long id);

    /**
     * Delete the "id" merchantUser.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
