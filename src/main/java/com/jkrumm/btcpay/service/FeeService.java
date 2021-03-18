package com.jkrumm.btcpay.service;

import com.jkrumm.btcpay.service.dto.FeeDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.jkrumm.btcpay.domain.Fee}.
 */
public interface FeeService {
    /**
     * Save a fee.
     *
     * @param feeDTO the entity to save.
     * @return the persisted entity.
     */
    FeeDTO save(FeeDTO feeDTO);

    /**
     * Partially updates a fee.
     *
     * @param feeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FeeDTO> partialUpdate(FeeDTO feeDTO);

    /**
     * Get all the fees.
     *
     * @return the list of entities.
     */
    List<FeeDTO> findAll();

    /**
     * Get the "id" fee.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FeeDTO> findOne(Long id);

    /**
     * Delete the "id" fee.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
