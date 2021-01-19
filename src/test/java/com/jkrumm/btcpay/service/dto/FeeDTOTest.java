package com.jkrumm.btcpay.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.jkrumm.btcpay.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FeeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FeeDTO.class);
        FeeDTO feeDTO1 = new FeeDTO();
        feeDTO1.setId(1L);
        FeeDTO feeDTO2 = new FeeDTO();
        assertThat(feeDTO1).isNotEqualTo(feeDTO2);
        feeDTO2.setId(feeDTO1.getId());
        assertThat(feeDTO1).isEqualTo(feeDTO2);
        feeDTO2.setId(2L);
        assertThat(feeDTO1).isNotEqualTo(feeDTO2);
        feeDTO1.setId(null);
        assertThat(feeDTO1).isNotEqualTo(feeDTO2);
    }
}
