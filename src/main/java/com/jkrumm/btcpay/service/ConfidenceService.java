package com.jkrumm.btcpay.service;

import com.jkrumm.btcpay.service.dto.ConfidenceDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.jkrumm.btcpay.domain.Confidence}.
 */
public interface ConfidenceService {
    /**
     * Save a confidence.
     *
     * @param confidenceDTO the entity to save.
     * @return the persisted entity.
     */
    ConfidenceDTO save(ConfidenceDTO confidenceDTO);

    /**
     * Partially updates a confidence.
     *
     * @param confidenceDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ConfidenceDTO> partialUpdate(ConfidenceDTO confidenceDTO);

    /**
     * Get all the confidences.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ConfidenceDTO> findAll(Pageable pageable);

    /**
     * Get the "id" confidence.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ConfidenceDTO> findOne(Long id);

    /**
     * Delete the "id" confidence.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
