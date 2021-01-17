package com.jkrumm.btcpay.service;

import com.jkrumm.btcpay.service.dto.MerchantDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.jkrumm.btcpay.domain.Merchant}.
 */
public interface MerchantService {
    /**
     * Save a merchant.
     *
     * @param merchantDTO the entity to save.
     * @return the persisted entity.
     */
    MerchantDTO save(MerchantDTO merchantDTO);

    /**
     * Get all the merchants.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MerchantDTO> findAll(Pageable pageable);

    /**
     * Get the "id" merchant.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MerchantDTO> findOne(Long id);

    /**
     * Delete the "id" merchant.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
