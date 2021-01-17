package com.jkrumm.btcpay.service;

import com.jkrumm.btcpay.service.dto.FeeDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
     * Get all the fees.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FeeDTO> findAll(Pageable pageable);

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
