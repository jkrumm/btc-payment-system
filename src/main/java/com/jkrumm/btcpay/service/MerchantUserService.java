package com.jkrumm.btcpay.service;

import com.jkrumm.btcpay.service.dto.MerchantUserDTO;
import java.util.List;
import java.util.Optional;

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
     * Partially updates a merchantUser.
     *
     * @param merchantUserDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MerchantUserDTO> partialUpdate(MerchantUserDTO merchantUserDTO);

    /**
     * Get all the merchantUsers.
     *
     * @return the list of entities.
     */
    List<MerchantUserDTO> findAll();

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
