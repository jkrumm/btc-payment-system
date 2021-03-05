package com.jkrumm.btcpay.service.impl;

import com.jkrumm.btcpay.domain.Transaction;
import com.jkrumm.btcpay.repository.TransactionRepository;
import com.jkrumm.btcpay.service.TransactionService;
import com.jkrumm.btcpay.service.dto.TransactionDTO;
import com.jkrumm.btcpay.service.mapper.TransactionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Transaction}.
 */
@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    private final Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);

    private final TransactionRepository transactionRepository;

    private final TransactionMapper transactionMapper;

    public TransactionServiceImpl(TransactionRepository transactionRepository, TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }

    @Override
    public TransactionDTO save(TransactionDTO transactionDTO) {
        log.debug("Request to save Transaction : {}", transactionDTO);
        Transaction transaction = transactionMapper.toEntity(transactionDTO);
        transaction = transactionRepository.save(transaction);
        return transactionMapper.toDto(transaction);
    }

    @Override
    public Optional<TransactionDTO> partialUpdate(TransactionDTO transactionDTO) {
        log.debug("Request to partially update Transaction : {}", transactionDTO);

        return transactionRepository
            .findById(transactionDTO.getId())
            .map(
                existingTransaction -> {
                    if (transactionDTO.getInitiatedAt() != null) {
                        existingTransaction.setInitiatedAt(transactionDTO.getInitiatedAt());
                    }

                    if (transactionDTO.getTransactionType() != null) {
                        existingTransaction.setTransactionType(transactionDTO.getTransactionType());
                    }

                    if (transactionDTO.getTxHash() != null) {
                        existingTransaction.setTxHash(transactionDTO.getTxHash());
                    }

                    if (transactionDTO.getExpectedAmount() != null) {
                        existingTransaction.setExpectedAmount(transactionDTO.getExpectedAmount());
                    }

                    if (transactionDTO.getActualAmount() != null) {
                        existingTransaction.setActualAmount(transactionDTO.getActualAmount());
                    }

                    if (transactionDTO.getTransactionFee() != null) {
                        existingTransaction.setTransactionFee(transactionDTO.getTransactionFee());
                    }

                    if (transactionDTO.getServiceFee() != null) {
                        existingTransaction.setServiceFee(transactionDTO.getServiceFee());
                    }

                    if (transactionDTO.getBtcUsd() != null) {
                        existingTransaction.setBtcUsd(transactionDTO.getBtcUsd());
                    }

                    if (transactionDTO.getAddress() != null) {
                        existingTransaction.setAddress(transactionDTO.getAddress());
                    }

                    if (transactionDTO.getAmount() != null) {
                        existingTransaction.setAmount(transactionDTO.getAmount());
                    }

                    return existingTransaction;
                }
            )
            .map(transactionRepository::save)
            .map(transactionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransactionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Transactions");
        return transactionRepository.findAll(pageable).map(transactionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TransactionDTO> findOne(Long id) {
        log.debug("Request to get Transaction : {}", id);
        return transactionRepository.findById(id).map(transactionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Transaction : {}", id);
        transactionRepository.deleteById(id);
    }
}
