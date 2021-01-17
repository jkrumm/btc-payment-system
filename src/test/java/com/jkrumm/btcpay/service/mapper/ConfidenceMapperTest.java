package com.jkrumm.btcpay.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ConfidenceMapperTest {
    private ConfidenceMapper confidenceMapper;

    @BeforeEach
    public void setUp() {
        confidenceMapper = new ConfidenceMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(confidenceMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(confidenceMapper.fromId(null)).isNull();
    }
}
