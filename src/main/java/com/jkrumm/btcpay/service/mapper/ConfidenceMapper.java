package com.jkrumm.btcpay.service.mapper;

import com.jkrumm.btcpay.domain.*;
import com.jkrumm.btcpay.service.dto.ConfidenceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Confidence} and its DTO {@link ConfidenceDTO}.
 */
@Mapper(componentModel = "spring", uses = { TransactionMapper.class })
public interface ConfidenceMapper extends EntityMapper<ConfidenceDTO, Confidence> {
    @Mapping(target = "transaction", source = "transaction", qualifiedByName = "id")
    ConfidenceDTO toDto(Confidence s);
}
