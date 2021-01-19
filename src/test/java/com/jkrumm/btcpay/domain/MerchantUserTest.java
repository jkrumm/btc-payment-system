package com.jkrumm.btcpay.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.jkrumm.btcpay.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MerchantUserTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MerchantUser.class);
        MerchantUser merchantUser1 = new MerchantUser();
        merchantUser1.setId(1L);
        MerchantUser merchantUser2 = new MerchantUser();
        merchantUser2.setId(merchantUser1.getId());
        assertThat(merchantUser1).isEqualTo(merchantUser2);
        merchantUser2.setId(2L);
        assertThat(merchantUser1).isNotEqualTo(merchantUser2);
        merchantUser1.setId(null);
        assertThat(merchantUser1).isNotEqualTo(merchantUser2);
    }
}
