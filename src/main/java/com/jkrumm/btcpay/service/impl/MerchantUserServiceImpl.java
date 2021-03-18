package com.jkrumm.btcpay.service.impl;

import com.jkrumm.btcpay.domain.MerchantUser;
import com.jkrumm.btcpay.repository.MerchantUserRepository;
import com.jkrumm.btcpay.service.MerchantUserService;
import com.jkrumm.btcpay.service.dto.MerchantUserDTO;
import com.jkrumm.btcpay.service.mapper.MerchantUserMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MerchantUser}.
 */
@Service
@Transactional
public class MerchantUserServiceImpl implements MerchantUserService {

    private final Logger log = LoggerFactory.getLogger(MerchantUserServiceImpl.class);

    private final MerchantUserRepository merchantUserRepository;

    private final MerchantUserMapper merchantUserMapper;

    public MerchantUserServiceImpl(MerchantUserRepository merchantUserRepository, MerchantUserMapper merchantUserMapper) {
        this.merchantUserRepository = merchantUserRepository;
        this.merchantUserMapper = merchantUserMapper;
    }

    @Override
    public MerchantUserDTO save(MerchantUserDTO merchantUserDTO) {
        log.debug("Request to save MerchantUser : {}", merchantUserDTO);
        MerchantUser merchantUser = merchantUserMapper.toEntity(merchantUserDTO);
        merchantUser = merchantUserRepository.save(merchantUser);
        return merchantUserMapper.toDto(merchantUser);
    }

    @Override
    public Optional<MerchantUserDTO> partialUpdate(MerchantUserDTO merchantUserDTO) {
        log.debug("Request to partially update MerchantUser : {}", merchantUserDTO);

        return merchantUserRepository
            .findById(merchantUserDTO.getId())
            .map(
                existingMerchantUser -> {
                    return existingMerchantUser;
                }
            )
            .map(merchantUserRepository::save)
            .map(merchantUserMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MerchantUserDTO> findAll() {
        log.debug("Request to get all MerchantUsers");
        return merchantUserRepository.findAll().stream().map(merchantUserMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MerchantUserDTO> findOne(Long id) {
        log.debug("Request to get MerchantUser : {}", id);
        return merchantUserRepository.findById(id).map(merchantUserMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MerchantUser : {}", id);
        merchantUserRepository.deleteById(id);
    }
}
