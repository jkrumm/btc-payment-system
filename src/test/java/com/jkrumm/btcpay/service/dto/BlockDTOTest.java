package com.jkrumm.btcpay.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.jkrumm.btcpay.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class BlockDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BlockDTO.class);
        BlockDTO blockDTO1 = new BlockDTO();
        blockDTO1.setId(1L);
        BlockDTO blockDTO2 = new BlockDTO();
        assertThat(blockDTO1).isNotEqualTo(blockDTO2);
        blockDTO2.setId(blockDTO1.getId());
        assertThat(blockDTO1).isEqualTo(blockDTO2);
        blockDTO2.setId(2L);
        assertThat(blockDTO1).isNotEqualTo(blockDTO2);
        blockDTO1.setId(null);
        assertThat(blockDTO1).isNotEqualTo(blockDTO2);
    }
}
