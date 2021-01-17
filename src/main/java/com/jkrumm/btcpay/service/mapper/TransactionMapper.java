package com.jkrumm.btcpay.service.mapper;

import com.jkrumm.btcpay.domain.*;
import com.jkrumm.btcpay.service.dto.TransactionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Transaction} and its DTO {@link TransactionDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class, BlockMapper.class })
public interface TransactionMapper extends EntityMapper<TransactionDTO, Transaction> {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "block.id", target = "blockId")
    TransactionDTO toDto(Transaction transaction);

    @Mapping(target = "confidences", ignore = true)
    @Mapping(target = "removeConfidence", ignore = true)
    @Mapping(source = "userId", target = "user")
    @Mapping(source = "blockId", target = "block")
    Transaction toEntity(TransactionDTO transactionDTO);

    default Transaction fromId(Long id) {
        if (id == null) {
            return null;
        }
        Transaction transaction = new Transaction();
        transaction.setId(id);
        return transaction;
    }
}
