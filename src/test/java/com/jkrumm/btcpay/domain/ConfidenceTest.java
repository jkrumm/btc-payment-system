package com.jkrumm.btcpay.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.jkrumm.btcpay.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class ConfidenceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Confidence.class);
        Confidence confidence1 = new Confidence();
        confidence1.setId(1L);
        Confidence confidence2 = new Confidence();
        confidence2.setId(confidence1.getId());
        assertThat(confidence1).isEqualTo(confidence2);
        confidence2.setId(2L);
        assertThat(confidence1).isNotEqualTo(confidence2);
        confidence1.setId(null);
        assertThat(confidence1).isNotEqualTo(confidence2);
    }
}
