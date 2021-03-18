package com.jkrumm.btcpay.service.impl;

import com.jkrumm.btcpay.domain.Confidence;
import com.jkrumm.btcpay.repository.ConfidenceRepository;
import com.jkrumm.btcpay.service.ConfidenceService;
import com.jkrumm.btcpay.service.dto.ConfidenceDTO;
import com.jkrumm.btcpay.service.mapper.ConfidenceMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Confidence}.
 */
@Service
@Transactional
public class ConfidenceServiceImpl implements ConfidenceService {

    private final Logger log = LoggerFactory.getLogger(ConfidenceServiceImpl.class);

    private final ConfidenceRepository confidenceRepository;

    private final ConfidenceMapper confidenceMapper;

    public ConfidenceServiceImpl(ConfidenceRepository confidenceRepository, ConfidenceMapper confidenceMapper) {
        this.confidenceRepository = confidenceRepository;
        this.confidenceMapper = confidenceMapper;
    }

    @Override
    public ConfidenceDTO save(ConfidenceDTO confidenceDTO) {
        log.debug("Request to save Confidence : {}", confidenceDTO);
        Confidence confidence = confidenceMapper.toEntity(confidenceDTO);
        confidence = confidenceRepository.save(confidence);
        return confidenceMapper.toDto(confidence);
    }

    @Override
    public Optional<ConfidenceDTO> partialUpdate(ConfidenceDTO confidenceDTO) {
        log.debug("Request to partially update Confidence : {}", confidenceDTO);

        return confidenceRepository
            .findById(confidenceDTO.getId())
            .map(
                existingConfidence -> {
                    if (confidenceDTO.getChangeAt() != null) {
                        existingConfidence.setChangeAt(confidenceDTO.getChangeAt());
                    }

                    if (confidenceDTO.getConfidenceType() != null) {
                        existingConfidence.setConfidenceType(confidenceDTO.getConfidenceType());
                    }

                    if (confidenceDTO.getConfirmations() != null) {
                        existingConfidence.setConfirmations(confidenceDTO.getConfirmations());
                    }

                    return existingConfidence;
                }
            )
            .map(confidenceRepository::save)
            .map(confidenceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ConfidenceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Confidences");
        return confidenceRepository.findAll(pageable).map(confidenceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ConfidenceDTO> findOne(Long id) {
        log.debug("Request to get Confidence : {}", id);
        return confidenceRepository.findById(id).map(confidenceMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Confidence : {}", id);
        confidenceRepository.deleteById(id);
    }
}
