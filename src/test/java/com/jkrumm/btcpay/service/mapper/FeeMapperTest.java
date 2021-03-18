package com.jkrumm.btcpay.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FeeMapperTest {

    private FeeMapper feeMapper;

    @BeforeEach
    public void setUp() {
        feeMapper = new FeeMapperImpl();
    }
}
