package com.jkrumm.btcpay.service.mapper;

import com.jkrumm.btcpay.domain.*;
import com.jkrumm.btcpay.service.dto.MerchantUserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MerchantUser} and its DTO {@link MerchantUserDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class, MerchantMapper.class })
public interface MerchantUserMapper extends EntityMapper<MerchantUserDTO, MerchantUser> {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "merchant.id", target = "merchantId")
    MerchantUserDTO toDto(MerchantUser merchantUser);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "merchantId", target = "merchant")
    MerchantUser toEntity(MerchantUserDTO merchantUserDTO);

    default MerchantUser fromId(Long id) {
        if (id == null) {
            return null;
        }
        MerchantUser merchantUser = new MerchantUser();
        merchantUser.setId(id);
        return merchantUser;
    }
}
