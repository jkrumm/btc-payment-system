package com.jkrumm.btcpay.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConfidenceMapperTest {

    private ConfidenceMapper confidenceMapper;

    @BeforeEach
    public void setUp() {
        confidenceMapper = new ConfidenceMapperImpl();
    }
}
