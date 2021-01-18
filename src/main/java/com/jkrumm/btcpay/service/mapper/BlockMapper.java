package com.jkrumm.btcpay.service.mapper;

import com.jkrumm.btcpay.domain.*;
import com.jkrumm.btcpay.service.dto.BlockDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Block} and its DTO {@link BlockDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BlockMapper extends EntityMapper<BlockDTO, Block> {
    @Mapping(source = "block.id", target = "blockId")
    BlockDTO toDto(Block block);

    @Mapping(target = "transactions", ignore = true)
    @Mapping(target = "removeTransaction", ignore = true)
    Block toEntity(BlockDTO blockDTO);

    default Block fromId(Long id) {
        if (id == null) {
            return null;
        }
        Block block = new Block();
        block.setId(id);
        return block;
    }
}
