package com.jkrumm.btcpay.service.impl;

import com.jkrumm.btcpay.domain.Block;
import com.jkrumm.btcpay.repository.BlockRepository;
import com.jkrumm.btcpay.service.BlockService;
import com.jkrumm.btcpay.service.dto.BlockDTO;
import com.jkrumm.btcpay.service.mapper.BlockMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Block}.
 */
@Service
@Transactional
public class BlockServiceImpl implements BlockService {
    private final Logger log = LoggerFactory.getLogger(BlockServiceImpl.class);

    private final BlockRepository blockRepository;

    private final BlockMapper blockMapper;

    public BlockServiceImpl(BlockRepository blockRepository, BlockMapper blockMapper) {
        this.blockRepository = blockRepository;
        this.blockMapper = blockMapper;
    }

    @Override
    public BlockDTO save(BlockDTO blockDTO) {
        log.debug("Request to save Block : {}", blockDTO);
        Block block = blockMapper.toEntity(blockDTO);
        block = blockRepository.save(block);
        return blockMapper.toDto(block);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BlockDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Blocks");
        return blockRepository.findAll(pageable).map(blockMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BlockDTO> findOne(Long id) {
        log.debug("Request to get Block : {}", id);
        return blockRepository.findById(id).map(blockMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public BlockDTO getLatestBlock() {
        log.info("Request to get latest Block");
        return blockMapper.toDto(blockRepository.findTopByOrderByIdDesc());
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Block : {}", id);
        blockRepository.deleteById(id);
    }
}
