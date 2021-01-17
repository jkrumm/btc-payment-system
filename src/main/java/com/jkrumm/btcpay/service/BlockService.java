package com.jkrumm.btcpay.service;

import com.jkrumm.btcpay.service.dto.BlockDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.jkrumm.btcpay.domain.Block}.
 */
public interface BlockService {
    /**
     * Save a block.
     *
     * @param blockDTO the entity to save.
     * @return the persisted entity.
     */
    BlockDTO save(BlockDTO blockDTO);

    /**
     * Get all the blocks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BlockDTO> findAll(Pageable pageable);

    /**
     * Get the "id" block.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BlockDTO> findOne(Long id);

    /**
     * Delete the "id" block.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
