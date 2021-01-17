package com.jkrumm.btcpay.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MerchantUserMapperTest {
    private MerchantUserMapper merchantUserMapper;

    @BeforeEach
    public void setUp() {
        merchantUserMapper = new MerchantUserMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(merchantUserMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(merchantUserMapper.fromId(null)).isNull();
    }
}
