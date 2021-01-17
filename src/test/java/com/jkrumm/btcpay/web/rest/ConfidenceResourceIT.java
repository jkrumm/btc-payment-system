package com.jkrumm.btcpay.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jkrumm.btcpay.BtcPaymentSystemApp;
import com.jkrumm.btcpay.domain.Confidence;
import com.jkrumm.btcpay.domain.enumeration.ConfidenceType;
import com.jkrumm.btcpay.repository.ConfidenceRepository;
import com.jkrumm.btcpay.service.ConfidenceService;
import com.jkrumm.btcpay.service.dto.ConfidenceDTO;
import com.jkrumm.btcpay.service.mapper.ConfidenceMapper;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ConfidenceResource} REST controller.
 */
@SpringBootTest(classes = BtcPaymentSystemApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ConfidenceResourceIT {
    private static final ConfidenceType DEFAULT_CONFIDENCE_TYPE = ConfidenceType.INCOMING;
    private static final ConfidenceType UPDATED_CONFIDENCE_TYPE = ConfidenceType.BUILDING;

    private static final Integer DEFAULT_CONFIRMATIONS = 1;
    private static final Integer UPDATED_CONFIRMATIONS = 2;

    @Autowired
    private ConfidenceRepository confidenceRepository;

    @Autowired
    private ConfidenceMapper confidenceMapper;

    @Autowired
    private ConfidenceService confidenceService;

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
        Confidence confidence = new Confidence().confidenceType(DEFAULT_CONFIDENCE_TYPE).confirmations(DEFAULT_CONFIRMATIONS);
        return confidence;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Confidence createUpdatedEntity(EntityManager em) {
        Confidence confidence = new Confidence().confidenceType(UPDATED_CONFIDENCE_TYPE).confirmations(UPDATED_CONFIRMATIONS);
        return confidence;
    }

    @BeforeEach
    public void initTest() {
        confidence = createEntity(em);
    }

    @Test
    @Transactional
    public void createConfidence() throws Exception {
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
        assertThat(testConfidence.getConfidenceType()).isEqualTo(DEFAULT_CONFIDENCE_TYPE);
        assertThat(testConfidence.getConfirmations()).isEqualTo(DEFAULT_CONFIRMATIONS);
    }

    @Test
    @Transactional
    public void createConfidenceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = confidenceRepository.findAll().size();

        // Create the Confidence with an existing ID
        confidence.setId(1L);
        ConfidenceDTO confidenceDTO = confidenceMapper.toDto(confidence);

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
    public void getAllConfidences() throws Exception {
        // Initialize the database
        confidenceRepository.saveAndFlush(confidence);

        // Get all the confidenceList
        restConfidenceMockMvc
            .perform(get("/api/confidences?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(confidence.getId().intValue())))
            .andExpect(jsonPath("$.[*].confidenceType").value(hasItem(DEFAULT_CONFIDENCE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].confirmations").value(hasItem(DEFAULT_CONFIRMATIONS)));
    }

    @Test
    @Transactional
    public void getConfidence() throws Exception {
        // Initialize the database
        confidenceRepository.saveAndFlush(confidence);

        // Get the confidence
        restConfidenceMockMvc
            .perform(get("/api/confidences/{id}", confidence.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(confidence.getId().intValue()))
            .andExpect(jsonPath("$.confidenceType").value(DEFAULT_CONFIDENCE_TYPE.toString()))
            .andExpect(jsonPath("$.confirmations").value(DEFAULT_CONFIRMATIONS));
    }

    @Test
    @Transactional
    public void getNonExistingConfidence() throws Exception {
        // Get the confidence
        restConfidenceMockMvc.perform(get("/api/confidences/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConfidence() throws Exception {
        // Initialize the database
        confidenceRepository.saveAndFlush(confidence);

        int databaseSizeBeforeUpdate = confidenceRepository.findAll().size();

        // Update the confidence
        Confidence updatedConfidence = confidenceRepository.findById(confidence.getId()).get();
        // Disconnect from session so that the updates on updatedConfidence are not directly saved in db
        em.detach(updatedConfidence);
        updatedConfidence.confidenceType(UPDATED_CONFIDENCE_TYPE).confirmations(UPDATED_CONFIRMATIONS);
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
        assertThat(testConfidence.getConfidenceType()).isEqualTo(UPDATED_CONFIDENCE_TYPE);
        assertThat(testConfidence.getConfirmations()).isEqualTo(UPDATED_CONFIRMATIONS);
    }

    @Test
    @Transactional
    public void updateNonExistingConfidence() throws Exception {
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
    public void deleteConfidence() throws Exception {
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
