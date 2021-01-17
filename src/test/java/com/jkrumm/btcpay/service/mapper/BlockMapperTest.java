package com.jkrumm.btcpay.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BlockMapperTest {
    private BlockMapper blockMapper;

    @BeforeEach
    public void setUp() {
        blockMapper = new BlockMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(blockMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(blockMapper.fromId(null)).isNull();
    }
}
