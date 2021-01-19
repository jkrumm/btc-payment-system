package com.jkrumm.btcpay.service.mapper;

import com.jkrumm.btcpay.domain.*;
import com.jkrumm.btcpay.service.dto.FeeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Fee} and its DTO {@link FeeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FeeMapper extends EntityMapper<FeeDTO, Fee> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FeeDTO toDtoId(Fee fee);
}
