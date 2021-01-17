package com.jkrumm.btcpay.service.mapper;

import com.jkrumm.btcpay.domain.*;
import com.jkrumm.btcpay.service.dto.MerchantDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Merchant} and its DTO {@link MerchantDTO}.
 */
@Mapper(componentModel = "spring", uses = { FeeMapper.class })
public interface MerchantMapper extends EntityMapper<MerchantDTO, Merchant> {
    @Mapping(source = "fee.id", target = "feeId")
    MerchantDTO toDto(Merchant merchant);

    @Mapping(source = "feeId", target = "fee")
    @Mapping(target = "merchantUsers", ignore = true)
    @Mapping(target = "removeMerchantUser", ignore = true)
    Merchant toEntity(MerchantDTO merchantDTO);

    default Merchant fromId(Long id) {
        if (id == null) {
            return null;
        }
        Merchant merchant = new Merchant();
        merchant.setId(id);
        return merchant;
    }
}
