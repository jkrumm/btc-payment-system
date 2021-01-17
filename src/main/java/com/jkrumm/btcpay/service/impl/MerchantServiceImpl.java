package com.jkrumm.btcpay.service.impl;

import com.jkrumm.btcpay.domain.Merchant;
import com.jkrumm.btcpay.repository.MerchantRepository;
import com.jkrumm.btcpay.service.MerchantService;
import com.jkrumm.btcpay.service.dto.MerchantDTO;
import com.jkrumm.btcpay.service.mapper.MerchantMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Merchant}.
 */
@Service
@Transactional
public class MerchantServiceImpl implements MerchantService {
    private final Logger log = LoggerFactory.getLogger(MerchantServiceImpl.class);

    private final MerchantRepository merchantRepository;

    private final MerchantMapper merchantMapper;

    public MerchantServiceImpl(MerchantRepository merchantRepository, MerchantMapper merchantMapper) {
        this.merchantRepository = merchantRepository;
        this.merchantMapper = merchantMapper;
    }

    @Override
    public MerchantDTO save(MerchantDTO merchantDTO) {
        log.debug("Request to save Merchant : {}", merchantDTO);
        Merchant merchant = merchantMapper.toEntity(merchantDTO);
        merchant = merchantRepository.save(merchant);
        return merchantMapper.toDto(merchant);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MerchantDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Merchants");
        return merchantRepository.findAll(pageable).map(merchantMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MerchantDTO> findOne(Long id) {
        log.debug("Request to get Merchant : {}", id);
        return merchantRepository.findById(id).map(merchantMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Merchant : {}", id);
        merchantRepository.deleteById(id);
    }
}
