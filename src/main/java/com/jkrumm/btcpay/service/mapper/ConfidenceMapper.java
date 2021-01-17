package com.jkrumm.btcpay.service.mapper;

import com.jkrumm.btcpay.domain.*;
import com.jkrumm.btcpay.service.dto.ConfidenceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Confidence} and its DTO {@link ConfidenceDTO}.
 */
@Mapper(componentModel = "spring", uses = { TransactionMapper.class })
public interface ConfidenceMapper extends EntityMapper<ConfidenceDTO, Confidence> {
    @Mapping(source = "transaction.id", target = "transactionId")
    ConfidenceDTO toDto(Confidence confidence);

    @Mapping(source = "transactionId", target = "transaction")
    Confidence toEntity(ConfidenceDTO confidenceDTO);

    default Confidence fromId(Long id) {
        if (id == null) {
            return null;
        }
        Confidence confidence = new Confidence();
        confidence.setId(id);
        return confidence;
    }
}
