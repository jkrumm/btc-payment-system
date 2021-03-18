package com.jkrumm.btcpay.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MerchantUserMapperTest {

    private MerchantUserMapper merchantUserMapper;

    @BeforeEach
    public void setUp() {
        merchantUserMapper = new MerchantUserMapperImpl();
    }
}
