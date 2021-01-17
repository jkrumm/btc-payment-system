package com.jkrumm.btcpay.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FeeMapperTest {
    private FeeMapper feeMapper;

    @BeforeEach
    public void setUp() {
        feeMapper = new FeeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(feeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(feeMapper.fromId(null)).isNull();
    }
}
