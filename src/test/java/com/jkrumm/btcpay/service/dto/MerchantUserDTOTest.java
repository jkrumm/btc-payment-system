package com.jkrumm.btcpay.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.jkrumm.btcpay.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MerchantUserDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MerchantUserDTO.class);
        MerchantUserDTO merchantUserDTO1 = new MerchantUserDTO();
        merchantUserDTO1.setId(1L);
        MerchantUserDTO merchantUserDTO2 = new MerchantUserDTO();
        assertThat(merchantUserDTO1).isNotEqualTo(merchantUserDTO2);
        merchantUserDTO2.setId(merchantUserDTO1.getId());
        assertThat(merchantUserDTO1).isEqualTo(merchantUserDTO2);
        merchantUserDTO2.setId(2L);
        assertThat(merchantUserDTO1).isNotEqualTo(merchantUserDTO2);
        merchantUserDTO1.setId(null);
        assertThat(merchantUserDTO1).isNotEqualTo(merchantUserDTO2);
    }
}
