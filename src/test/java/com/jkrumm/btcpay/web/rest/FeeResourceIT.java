package com.jkrumm.btcpay.web.rest;

import static com.jkrumm.btcpay.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jkrumm.btcpay.IntegrationTest;
import com.jkrumm.btcpay.domain.Fee;
import com.jkrumm.btcpay.domain.enumeration.FeeType;
import com.jkrumm.btcpay.repository.FeeRepository;
import com.jkrumm.btcpay.service.dto.FeeDTO;
import com.jkrumm.btcpay.service.mapper.FeeMapper;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link FeeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FeeResourceIT {

    private static final FeeType DEFAULT_FEE_TYPE = FeeType.ZERO;
    private static final FeeType UPDATED_FEE_TYPE = FeeType.LOW;

    private static final BigDecimal DEFAULT_PERCENT = new BigDecimal(1);
    private static final BigDecimal UPDATED_PERCENT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PERCENT_SECURE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PERCENT_SECURE = new BigDecimal(2);

    @Autowired
    private FeeRepository feeRepository;

    @Autowired
    private FeeMapper feeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFeeMockMvc;

    private Fee fee;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fee createEntity(EntityManager em) {
        Fee fee = new Fee().feeType(DEFAULT_FEE_TYPE).percent(DEFAULT_PERCENT).percentSecure(DEFAULT_PERCENT_SECURE);
        return fee;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fee createUpdatedEntity(EntityManager em) {
        Fee fee = new Fee().feeType(UPDATED_FEE_TYPE).percent(UPDATED_PERCENT).percentSecure(UPDATED_PERCENT_SECURE);
        return fee;
    }

    @BeforeEach
    public void initTest() {
        fee = createEntity(em);
    }

    @Test
    @Transactional
    void createFee() throws Exception {
        int databaseSizeBeforeCreate = feeRepository.findAll().size();
        // Create the Fee
        FeeDTO feeDTO = feeMapper.toDto(fee);
        restFeeMockMvc
            .perform(post("/api/fees").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feeDTO)))
            .andExpect(status().isCreated());

        // Validate the Fee in the database
        List<Fee> feeList = feeRepository.findAll();
        assertThat(feeList).hasSize(databaseSizeBeforeCreate + 1);
        Fee testFee = feeList.get(feeList.size() - 1);
        assertThat(testFee.getFeeType()).isEqualTo(DEFAULT_FEE_TYPE);
        assertThat(testFee.getPercent()).isEqualByComparingTo(DEFAULT_PERCENT);
        assertThat(testFee.getPercentSecure()).isEqualByComparingTo(DEFAULT_PERCENT_SECURE);
    }

    @Test
    @Transactional
    void createFeeWithExistingId() throws Exception {
        // Create the Fee with an existing ID
        fee.setId(1L);
        FeeDTO feeDTO = feeMapper.toDto(fee);

        int databaseSizeBeforeCreate = feeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFeeMockMvc
            .perform(post("/api/fees").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Fee in the database
        List<Fee> feeList = feeRepository.findAll();
        assertThat(feeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFeeTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = feeRepository.findAll().size();
        // set the field null
        fee.setFeeType(null);

        // Create the Fee, which fails.
        FeeDTO feeDTO = feeMapper.toDto(fee);

        restFeeMockMvc
            .perform(post("/api/fees").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feeDTO)))
            .andExpect(status().isBadRequest());

        List<Fee> feeList = feeRepository.findAll();
        assertThat(feeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPercentIsRequired() throws Exception {
        int databaseSizeBeforeTest = feeRepository.findAll().size();
        // set the field null
        fee.setPercent(null);

        // Create the Fee, which fails.
        FeeDTO feeDTO = feeMapper.toDto(fee);

        restFeeMockMvc
            .perform(post("/api/fees").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feeDTO)))
            .andExpect(status().isBadRequest());

        List<Fee> feeList = feeRepository.findAll();
        assertThat(feeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPercentSecureIsRequired() throws Exception {
        int databaseSizeBeforeTest = feeRepository.findAll().size();
        // set the field null
        fee.setPercentSecure(null);

        // Create the Fee, which fails.
        FeeDTO feeDTO = feeMapper.toDto(fee);

        restFeeMockMvc
            .perform(post("/api/fees").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feeDTO)))
            .andExpect(status().isBadRequest());

        List<Fee> feeList = feeRepository.findAll();
        assertThat(feeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFees() throws Exception {
        // Initialize the database
        feeRepository.saveAndFlush(fee);

        // Get all the feeList
        restFeeMockMvc
            .perform(get("/api/fees?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fee.getId().intValue())))
            .andExpect(jsonPath("$.[*].feeType").value(hasItem(DEFAULT_FEE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].percent").value(hasItem(sameNumber(DEFAULT_PERCENT))))
            .andExpect(jsonPath("$.[*].percentSecure").value(hasItem(sameNumber(DEFAULT_PERCENT_SECURE))));
    }

    @Test
    @Transactional
    void getFee() throws Exception {
        // Initialize the database
        feeRepository.saveAndFlush(fee);

        // Get the fee
        restFeeMockMvc
            .perform(get("/api/fees/{id}", fee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fee.getId().intValue()))
            .andExpect(jsonPath("$.feeType").value(DEFAULT_FEE_TYPE.toString()))
            .andExpect(jsonPath("$.percent").value(sameNumber(DEFAULT_PERCENT)))
            .andExpect(jsonPath("$.percentSecure").value(sameNumber(DEFAULT_PERCENT_SECURE)));
    }

    @Test
    @Transactional
    void getNonExistingFee() throws Exception {
        // Get the fee
        restFeeMockMvc.perform(get("/api/fees/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void updateFee() throws Exception {
        // Initialize the database
        feeRepository.saveAndFlush(fee);

        int databaseSizeBeforeUpdate = feeRepository.findAll().size();

        // Update the fee
        Fee updatedFee = feeRepository.findById(fee.getId()).get();
        // Disconnect from session so that the updates on updatedFee are not directly saved in db
        em.detach(updatedFee);
        updatedFee.feeType(UPDATED_FEE_TYPE).percent(UPDATED_PERCENT).percentSecure(UPDATED_PERCENT_SECURE);
        FeeDTO feeDTO = feeMapper.toDto(updatedFee);

        restFeeMockMvc
            .perform(put("/api/fees").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feeDTO)))
            .andExpect(status().isOk());

        // Validate the Fee in the database
        List<Fee> feeList = feeRepository.findAll();
        assertThat(feeList).hasSize(databaseSizeBeforeUpdate);
        Fee testFee = feeList.get(feeList.size() - 1);
        assertThat(testFee.getFeeType()).isEqualTo(UPDATED_FEE_TYPE);
        assertThat(testFee.getPercent()).isEqualTo(UPDATED_PERCENT);
        assertThat(testFee.getPercentSecure()).isEqualTo(UPDATED_PERCENT_SECURE);
    }

    @Test
    @Transactional
    void updateNonExistingFee() throws Exception {
        int databaseSizeBeforeUpdate = feeRepository.findAll().size();

        // Create the Fee
        FeeDTO feeDTO = feeMapper.toDto(fee);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFeeMockMvc
            .perform(put("/api/fees").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Fee in the database
        List<Fee> feeList = feeRepository.findAll();
        assertThat(feeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFeeWithPatch() throws Exception {
        // Initialize the database
        feeRepository.saveAndFlush(fee);

        int databaseSizeBeforeUpdate = feeRepository.findAll().size();

        // Update the fee using partial update
        Fee partialUpdatedFee = new Fee();
        partialUpdatedFee.setId(fee.getId());

        restFeeMockMvc
            .perform(
                patch("/api/fees").contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(partialUpdatedFee))
            )
            .andExpect(status().isOk());

        // Validate the Fee in the database
        List<Fee> feeList = feeRepository.findAll();
        assertThat(feeList).hasSize(databaseSizeBeforeUpdate);
        Fee testFee = feeList.get(feeList.size() - 1);
        assertThat(testFee.getFeeType()).isEqualTo(DEFAULT_FEE_TYPE);
        assertThat(testFee.getPercent()).isEqualByComparingTo(DEFAULT_PERCENT);
        assertThat(testFee.getPercentSecure()).isEqualByComparingTo(DEFAULT_PERCENT_SECURE);
    }

    @Test
    @Transactional
    void fullUpdateFeeWithPatch() throws Exception {
        // Initialize the database
        feeRepository.saveAndFlush(fee);

        int databaseSizeBeforeUpdate = feeRepository.findAll().size();

        // Update the fee using partial update
        Fee partialUpdatedFee = new Fee();
        partialUpdatedFee.setId(fee.getId());

        partialUpdatedFee.feeType(UPDATED_FEE_TYPE).percent(UPDATED_PERCENT).percentSecure(UPDATED_PERCENT_SECURE);

        restFeeMockMvc
            .perform(
                patch("/api/fees").contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(partialUpdatedFee))
            )
            .andExpect(status().isOk());

        // Validate the Fee in the database
        List<Fee> feeList = feeRepository.findAll();
        assertThat(feeList).hasSize(databaseSizeBeforeUpdate);
        Fee testFee = feeList.get(feeList.size() - 1);
        assertThat(testFee.getFeeType()).isEqualTo(UPDATED_FEE_TYPE);
        assertThat(testFee.getPercent()).isEqualByComparingTo(UPDATED_PERCENT);
        assertThat(testFee.getPercentSecure()).isEqualByComparingTo(UPDATED_PERCENT_SECURE);
    }

    @Test
    @Transactional
    void partialUpdateFeeShouldThrown() throws Exception {
        // Update the fee without id should throw
        Fee partialUpdatedFee = new Fee();

        restFeeMockMvc
            .perform(
                patch("/api/fees").contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(partialUpdatedFee))
            )
            .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void deleteFee() throws Exception {
        // Initialize the database
        feeRepository.saveAndFlush(fee);

        int databaseSizeBeforeDelete = feeRepository.findAll().size();

        // Delete the fee
        restFeeMockMvc.perform(delete("/api/fees/{id}", fee.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Fee> feeList = feeRepository.findAll();
        assertThat(feeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
