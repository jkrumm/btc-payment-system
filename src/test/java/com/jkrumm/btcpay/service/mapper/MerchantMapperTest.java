package com.jkrumm.btcpay.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MerchantMapperTest {

    private MerchantMapper merchantMapper;

    @BeforeEach
    public void setUp() {
        merchantMapper = new MerchantMapperImpl();
    }
}
