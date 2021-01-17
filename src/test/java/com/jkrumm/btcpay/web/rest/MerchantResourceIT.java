package com.jkrumm.btcpay.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jkrumm.btcpay.BtcPaymentSystemApp;
import com.jkrumm.btcpay.domain.Merchant;
import com.jkrumm.btcpay.repository.MerchantRepository;
import com.jkrumm.btcpay.service.MerchantService;
import com.jkrumm.btcpay.service.dto.MerchantDTO;
import com.jkrumm.btcpay.service.mapper.MerchantMapper;
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
 * Integration tests for the {@link MerchantResource} REST controller.
 */
@SpringBootTest(classes = BtcPaymentSystemApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MerchantResourceIT {
    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = ";v,N*@Y..m/Zt<";
    private static final String UPDATED_EMAIL = "B@&.!";

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private MerchantMapper merchantMapper;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMerchantMockMvc;

    private Merchant merchant;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Merchant createEntity(EntityManager em) {
        Merchant merchant = new Merchant().name(DEFAULT_NAME).email(DEFAULT_EMAIL);
        return merchant;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Merchant createUpdatedEntity(EntityManager em) {
        Merchant merchant = new Merchant().name(UPDATED_NAME).email(UPDATED_EMAIL);
        return merchant;
    }

    @BeforeEach
    public void initTest() {
        merchant = createEntity(em);
    }

    @Test
    @Transactional
    public void createMerchant() throws Exception {
        int databaseSizeBeforeCreate = merchantRepository.findAll().size();
        // Create the Merchant
        MerchantDTO merchantDTO = merchantMapper.toDto(merchant);
        restMerchantMockMvc
            .perform(post("/api/merchants").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(merchantDTO)))
            .andExpect(status().isCreated());

        // Validate the Merchant in the database
        List<Merchant> merchantList = merchantRepository.findAll();
        assertThat(merchantList).hasSize(databaseSizeBeforeCreate + 1);
        Merchant testMerchant = merchantList.get(merchantList.size() - 1);
        assertThat(testMerchant.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMerchant.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void createMerchantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = merchantRepository.findAll().size();

        // Create the Merchant with an existing ID
        merchant.setId(1L);
        MerchantDTO merchantDTO = merchantMapper.toDto(merchant);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMerchantMockMvc
            .perform(post("/api/merchants").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(merchantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Merchant in the database
        List<Merchant> merchantList = merchantRepository.findAll();
        assertThat(merchantList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = merchantRepository.findAll().size();
        // set the field null
        merchant.setName(null);

        // Create the Merchant, which fails.
        MerchantDTO merchantDTO = merchantMapper.toDto(merchant);

        restMerchantMockMvc
            .perform(post("/api/merchants").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(merchantDTO)))
            .andExpect(status().isBadRequest());

        List<Merchant> merchantList = merchantRepository.findAll();
        assertThat(merchantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = merchantRepository.findAll().size();
        // set the field null
        merchant.setEmail(null);

        // Create the Merchant, which fails.
        MerchantDTO merchantDTO = merchantMapper.toDto(merchant);

        restMerchantMockMvc
            .perform(post("/api/merchants").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(merchantDTO)))
            .andExpect(status().isBadRequest());

        List<Merchant> merchantList = merchantRepository.findAll();
        assertThat(merchantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMerchants() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList
        restMerchantMockMvc
            .perform(get("/api/merchants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(merchant.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));
    }

    @Test
    @Transactional
    public void getMerchant() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get the merchant
        restMerchantMockMvc
            .perform(get("/api/merchants/{id}", merchant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(merchant.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL));
    }

    @Test
    @Transactional
    public void getNonExistingMerchant() throws Exception {
        // Get the merchant
        restMerchantMockMvc.perform(get("/api/merchants/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMerchant() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        int databaseSizeBeforeUpdate = merchantRepository.findAll().size();

        // Update the merchant
        Merchant updatedMerchant = merchantRepository.findById(merchant.getId()).get();
        // Disconnect from session so that the updates on updatedMerchant are not directly saved in db
        em.detach(updatedMerchant);
        updatedMerchant.name(UPDATED_NAME).email(UPDATED_EMAIL);
        MerchantDTO merchantDTO = merchantMapper.toDto(updatedMerchant);

        restMerchantMockMvc
            .perform(put("/api/merchants").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(merchantDTO)))
            .andExpect(status().isOk());

        // Validate the Merchant in the database
        List<Merchant> merchantList = merchantRepository.findAll();
        assertThat(merchantList).hasSize(databaseSizeBeforeUpdate);
        Merchant testMerchant = merchantList.get(merchantList.size() - 1);
        assertThat(testMerchant.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMerchant.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingMerchant() throws Exception {
        int databaseSizeBeforeUpdate = merchantRepository.findAll().size();

        // Create the Merchant
        MerchantDTO merchantDTO = merchantMapper.toDto(merchant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMerchantMockMvc
            .perform(put("/api/merchants").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(merchantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Merchant in the database
        List<Merchant> merchantList = merchantRepository.findAll();
        assertThat(merchantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMerchant() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        int databaseSizeBeforeDelete = merchantRepository.findAll().size();

        // Delete the merchant
        restMerchantMockMvc
            .perform(delete("/api/merchants/{id}", merchant.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Merchant> merchantList = merchantRepository.findAll();
        assertThat(merchantList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
