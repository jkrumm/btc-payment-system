package com.jkrumm.btcpay.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jkrumm.btcpay.IntegrationTest;
import com.jkrumm.btcpay.domain.Confidence;
import com.jkrumm.btcpay.domain.enumeration.ConfidenceType;
import com.jkrumm.btcpay.repository.ConfidenceRepository;
import com.jkrumm.btcpay.service.dto.ConfidenceDTO;
import com.jkrumm.btcpay.service.mapper.ConfidenceMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link ConfidenceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ConfidenceResourceIT {

    private static final Instant DEFAULT_CHANGE_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CHANGE_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final ConfidenceType DEFAULT_CONFIDENCE_TYPE = ConfidenceType.INCOMING;
    private static final ConfidenceType UPDATED_CONFIDENCE_TYPE = ConfidenceType.BUILDING;

    private static final Integer DEFAULT_CONFIRMATIONS = 0;
    private static final Integer UPDATED_CONFIRMATIONS = 1;

    @Autowired
    private ConfidenceRepository confidenceRepository;

    @Autowired
    private ConfidenceMapper confidenceMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConfidenceMockMvc;

    private Confidence confidence;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Confidence createEntity(EntityManager em) {
        Confidence confidence = new Confidence()
            .changeAt(DEFAULT_CHANGE_AT)
            .confidenceType(DEFAULT_CONFIDENCE_TYPE)
            .confirmations(DEFAULT_CONFIRMATIONS);
        return confidence;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Confidence createUpdatedEntity(EntityManager em) {
        Confidence confidence = new Confidence()
            .changeAt(UPDATED_CHANGE_AT)
            .confidenceType(UPDATED_CONFIDENCE_TYPE)
            .confirmations(UPDATED_CONFIRMATIONS);
        return confidence;
    }

    @BeforeEach
    public void initTest() {
        confidence = createEntity(em);
    }

    @Test
    @Transactional
    void createConfidence() throws Exception {
        int databaseSizeBeforeCreate = confidenceRepository.findAll().size();
        // Create the Confidence
        ConfidenceDTO confidenceDTO = confidenceMapper.toDto(confidence);
        restConfidenceMockMvc
            .perform(
                post("/api/confidences").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(confidenceDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Confidence in the database
        List<Confidence> confidenceList = confidenceRepository.findAll();
        assertThat(confidenceList).hasSize(databaseSizeBeforeCreate + 1);
        Confidence testConfidence = confidenceList.get(confidenceList.size() - 1);
        assertThat(testConfidence.getChangeAt()).isEqualTo(DEFAULT_CHANGE_AT);
        assertThat(testConfidence.getConfidenceType()).isEqualTo(DEFAULT_CONFIDENCE_TYPE);
        assertThat(testConfidence.getConfirmations()).isEqualTo(DEFAULT_CONFIRMATIONS);
    }

    @Test
    @Transactional
    void createConfidenceWithExistingId() throws Exception {
        // Create the Confidence with an existing ID
        confidence.setId(1L);
        ConfidenceDTO confidenceDTO = confidenceMapper.toDto(confidence);

        int databaseSizeBeforeCreate = confidenceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restConfidenceMockMvc
            .perform(
                post("/api/confidences").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(confidenceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Confidence in the database
        List<Confidence> confidenceList = confidenceRepository.findAll();
        assertThat(confidenceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkChangeAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = confidenceRepository.findAll().size();
        // set the field null
        confidence.setChangeAt(null);

        // Create the Confidence, which fails.
        ConfidenceDTO confidenceDTO = confidenceMapper.toDto(confidence);

        restConfidenceMockMvc
            .perform(
                post("/api/confidences").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(confidenceDTO))
            )
            .andExpect(status().isBadRequest());

        List<Confidence> confidenceList = confidenceRepository.findAll();
        assertThat(confidenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkConfidenceTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = confidenceRepository.findAll().size();
        // set the field null
        confidence.setConfidenceType(null);

        // Create the Confidence, which fails.
        ConfidenceDTO confidenceDTO = confidenceMapper.toDto(confidence);

        restConfidenceMockMvc
            .perform(
                post("/api/confidences").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(confidenceDTO))
            )
            .andExpect(status().isBadRequest());

        List<Confidence> confidenceList = confidenceRepository.findAll();
        assertThat(confidenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkConfirmationsIsRequired() throws Exception {
        int databaseSizeBeforeTest = confidenceRepository.findAll().size();
        // set the field null
        confidence.setConfirmations(null);

        // Create the Confidence, which fails.
        ConfidenceDTO confidenceDTO = confidenceMapper.toDto(confidence);

        restConfidenceMockMvc
            .perform(
                post("/api/confidences").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(confidenceDTO))
            )
            .andExpect(status().isBadRequest());

        List<Confidence> confidenceList = confidenceRepository.findAll();
        assertThat(confidenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllConfidences() throws Exception {
        // Initialize the database
        confidenceRepository.saveAndFlush(confidence);

        // Get all the confidenceList
        restConfidenceMockMvc
            .perform(get("/api/confidences?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(confidence.getId().intValue())))
            .andExpect(jsonPath("$.[*].changeAt").value(hasItem(DEFAULT_CHANGE_AT.toString())))
            .andExpect(jsonPath("$.[*].confidenceType").value(hasItem(DEFAULT_CONFIDENCE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].confirmations").value(hasItem(DEFAULT_CONFIRMATIONS)));
    }

    @Test
    @Transactional
    void getConfidence() throws Exception {
        // Initialize the database
        confidenceRepository.saveAndFlush(confidence);

        // Get the confidence
        restConfidenceMockMvc
            .perform(get("/api/confidences/{id}", confidence.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(confidence.getId().intValue()))
            .andExpect(jsonPath("$.changeAt").value(DEFAULT_CHANGE_AT.toString()))
            .andExpect(jsonPath("$.confidenceType").value(DEFAULT_CONFIDENCE_TYPE.toString()))
            .andExpect(jsonPath("$.confirmations").value(DEFAULT_CONFIRMATIONS));
    }

    @Test
    @Transactional
    void getNonExistingConfidence() throws Exception {
        // Get the confidence
        restConfidenceMockMvc.perform(get("/api/confidences/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void updateConfidence() throws Exception {
        // Initialize the database
        confidenceRepository.saveAndFlush(confidence);

        int databaseSizeBeforeUpdate = confidenceRepository.findAll().size();

        // Update the confidence
        Confidence updatedConfidence = confidenceRepository.findById(confidence.getId()).get();
        // Disconnect from session so that the updates on updatedConfidence are not directly saved in db
        em.detach(updatedConfidence);
        updatedConfidence.changeAt(UPDATED_CHANGE_AT).confidenceType(UPDATED_CONFIDENCE_TYPE).confirmations(UPDATED_CONFIRMATIONS);
        ConfidenceDTO confidenceDTO = confidenceMapper.toDto(updatedConfidence);

        restConfidenceMockMvc
            .perform(
                put("/api/confidences").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(confidenceDTO))
            )
            .andExpect(status().isOk());

        // Validate the Confidence in the database
        List<Confidence> confidenceList = confidenceRepository.findAll();
        assertThat(confidenceList).hasSize(databaseSizeBeforeUpdate);
        Confidence testConfidence = confidenceList.get(confidenceList.size() - 1);
        assertThat(testConfidence.getChangeAt()).isEqualTo(UPDATED_CHANGE_AT);
        assertThat(testConfidence.getConfidenceType()).isEqualTo(UPDATED_CONFIDENCE_TYPE);
        assertThat(testConfidence.getConfirmations()).isEqualTo(UPDATED_CONFIRMATIONS);
    }

    @Test
    @Transactional
    void updateNonExistingConfidence() throws Exception {
        int databaseSizeBeforeUpdate = confidenceRepository.findAll().size();

        // Create the Confidence
        ConfidenceDTO confidenceDTO = confidenceMapper.toDto(confidence);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConfidenceMockMvc
            .perform(
                put("/api/confidences").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(confidenceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Confidence in the database
        List<Confidence> confidenceList = confidenceRepository.findAll();
        assertThat(confidenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateConfidenceWithPatch() throws Exception {
        // Initialize the database
        confidenceRepository.saveAndFlush(confidence);

        int databaseSizeBeforeUpdate = confidenceRepository.findAll().size();

        // Update the confidence using partial update
        Confidence partialUpdatedConfidence = new Confidence();
        partialUpdatedConfidence.setId(confidence.getId());

        partialUpdatedConfidence.changeAt(UPDATED_CHANGE_AT).confidenceType(UPDATED_CONFIDENCE_TYPE);

        restConfidenceMockMvc
            .perform(
                patch("/api/confidences")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConfidence))
            )
            .andExpect(status().isOk());

        // Validate the Confidence in the database
        List<Confidence> confidenceList = confidenceRepository.findAll();
        assertThat(confidenceList).hasSize(databaseSizeBeforeUpdate);
        Confidence testConfidence = confidenceList.get(confidenceList.size() - 1);
        assertThat(testConfidence.getChangeAt()).isEqualTo(UPDATED_CHANGE_AT);
        assertThat(testConfidence.getConfidenceType()).isEqualTo(UPDATED_CONFIDENCE_TYPE);
        assertThat(testConfidence.getConfirmations()).isEqualTo(DEFAULT_CONFIRMATIONS);
    }

    @Test
    @Transactional
    void fullUpdateConfidenceWithPatch() throws Exception {
        // Initialize the database
        confidenceRepository.saveAndFlush(confidence);

        int databaseSizeBeforeUpdate = confidenceRepository.findAll().size();

        // Update the confidence using partial update
        Confidence partialUpdatedConfidence = new Confidence();
        partialUpdatedConfidence.setId(confidence.getId());

        partialUpdatedConfidence.changeAt(UPDATED_CHANGE_AT).confidenceType(UPDATED_CONFIDENCE_TYPE).confirmations(UPDATED_CONFIRMATIONS);

        restConfidenceMockMvc
            .perform(
                patch("/api/confidences")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConfidence))
            )
            .andExpect(status().isOk());

        // Validate the Confidence in the database
        List<Confidence> confidenceList = confidenceRepository.findAll();
        assertThat(confidenceList).hasSize(databaseSizeBeforeUpdate);
        Confidence testConfidence = confidenceList.get(confidenceList.size() - 1);
        assertThat(testConfidence.getChangeAt()).isEqualTo(UPDATED_CHANGE_AT);
        assertThat(testConfidence.getConfidenceType()).isEqualTo(UPDATED_CONFIDENCE_TYPE);
        assertThat(testConfidence.getConfirmations()).isEqualTo(UPDATED_CONFIRMATIONS);
    }

    @Test
    @Transactional
    void partialUpdateConfidenceShouldThrown() throws Exception {
        // Update the confidence without id should throw
        Confidence partialUpdatedConfidence = new Confidence();

        restConfidenceMockMvc
            .perform(
                patch("/api/confidences")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConfidence))
            )
            .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void deleteConfidence() throws Exception {
        // Initialize the database
        confidenceRepository.saveAndFlush(confidence);

        int databaseSizeBeforeDelete = confidenceRepository.findAll().size();

        // Delete the confidence
        restConfidenceMockMvc
            .perform(delete("/api/confidences/{id}", confidence.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Confidence> confidenceList = confidenceRepository.findAll();
        assertThat(confidenceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
