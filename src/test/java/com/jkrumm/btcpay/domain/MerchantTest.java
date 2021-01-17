package com.jkrumm.btcpay.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.jkrumm.btcpay.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class MerchantTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Merchant.class);
        Merchant merchant1 = new Merchant();
        merchant1.setId(1L);
        Merchant merchant2 = new Merchant();
        merchant2.setId(merchant1.getId());
        assertThat(merchant1).isEqualTo(merchant2);
        merchant2.setId(2L);
        assertThat(merchant1).isNotEqualTo(merchant2);
        merchant1.setId(null);
        assertThat(merchant1).isNotEqualTo(merchant2);
    }
}
