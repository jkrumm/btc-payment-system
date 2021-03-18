package com.jkrumm.btcpay.service.mapper;

import com.jkrumm.btcpay.domain.*;
import com.jkrumm.btcpay.service.dto.MerchantUserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MerchantUser} and its DTO {@link MerchantUserDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class, MerchantMapper.class })
public interface MerchantUserMapper extends EntityMapper<MerchantUserDTO, MerchantUser> {
    @Mapping(target = "user", source = "user", qualifiedByName = "id")
    @Mapping(target = "merchant", source = "merchant", qualifiedByName = "id")
    MerchantUserDTO toDto(MerchantUser s);
}
