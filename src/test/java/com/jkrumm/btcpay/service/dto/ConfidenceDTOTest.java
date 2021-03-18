package com.jkrumm.btcpay.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.jkrumm.btcpay.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ConfidenceDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConfidenceDTO.class);
        ConfidenceDTO confidenceDTO1 = new ConfidenceDTO();
        confidenceDTO1.setId(1L);
        ConfidenceDTO confidenceDTO2 = new ConfidenceDTO();
        assertThat(confidenceDTO1).isNotEqualTo(confidenceDTO2);
        confidenceDTO2.setId(confidenceDTO1.getId());
        assertThat(confidenceDTO1).isEqualTo(confidenceDTO2);
        confidenceDTO2.setId(2L);
        assertThat(confidenceDTO1).isNotEqualTo(confidenceDTO2);
        confidenceDTO1.setId(null);
        assertThat(confidenceDTO1).isNotEqualTo(confidenceDTO2);
    }
}
