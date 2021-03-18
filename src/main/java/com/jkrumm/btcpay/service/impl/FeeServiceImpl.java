package com.jkrumm.btcpay.service.impl;

import com.jkrumm.btcpay.domain.Fee;
import com.jkrumm.btcpay.repository.FeeRepository;
import com.jkrumm.btcpay.service.FeeService;
import com.jkrumm.btcpay.service.dto.FeeDTO;
import com.jkrumm.btcpay.service.mapper.FeeMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Fee}.
 */
@Service
@Transactional
public class FeeServiceImpl implements FeeService {

    private final Logger log = LoggerFactory.getLogger(FeeServiceImpl.class);

    private final FeeRepository feeRepository;

    private final FeeMapper feeMapper;

    public FeeServiceImpl(FeeRepository feeRepository, FeeMapper feeMapper) {
        this.feeRepository = feeRepository;
        this.feeMapper = feeMapper;
    }

    @Override
    public FeeDTO save(FeeDTO feeDTO) {
        log.debug("Request to save Fee : {}", feeDTO);
        Fee fee = feeMapper.toEntity(feeDTO);
        fee = feeRepository.save(fee);
        return feeMapper.toDto(fee);
    }

    @Override
    public Optional<FeeDTO> partialUpdate(FeeDTO feeDTO) {
        log.debug("Request to partially update Fee : {}", feeDTO);

        return feeRepository
            .findById(feeDTO.getId())
            .map(
                existingFee -> {
                    if (feeDTO.getFeeType() != null) {
                        existingFee.setFeeType(feeDTO.getFeeType());
                    }

                    if (feeDTO.getPercent() != null) {
                        existingFee.setPercent(feeDTO.getPercent());
                    }

                    if (feeDTO.getPercentSecure() != null) {
                        existingFee.setPercentSecure(feeDTO.getPercentSecure());
                    }

                    return existingFee;
                }
            )
            .map(feeRepository::save)
            .map(feeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FeeDTO> findAll() {
        log.debug("Request to get all Fees");
        return feeRepository.findAll().stream().map(feeMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FeeDTO> findOne(Long id) {
        log.debug("Request to get Fee : {}", id);
        return feeRepository.findById(id).map(feeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Fee : {}", id);
        feeRepository.deleteById(id);
    }
}
