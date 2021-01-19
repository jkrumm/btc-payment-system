package com.jkrumm.btcpay.service.mapper;

import com.jkrumm.btcpay.domain.*;
import com.jkrumm.btcpay.service.dto.MerchantDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Merchant} and its DTO {@link MerchantDTO}.
 */
@Mapper(componentModel = "spring", uses = { FeeMapper.class })
public interface MerchantMapper extends EntityMapper<MerchantDTO, Merchant> {
    @Mapping(target = "fee", source = "fee", qualifiedByName = "id")
    MerchantDTO toDto(Merchant s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MerchantDTO toDtoId(Merchant merchant);
}
