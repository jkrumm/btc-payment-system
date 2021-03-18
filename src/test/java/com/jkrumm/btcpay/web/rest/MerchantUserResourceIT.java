package com.jkrumm.btcpay.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jkrumm.btcpay.IntegrationTest;
import com.jkrumm.btcpay.domain.MerchantUser;
import com.jkrumm.btcpay.repository.MerchantUserRepository;
import com.jkrumm.btcpay.service.dto.MerchantUserDTO;
import com.jkrumm.btcpay.service.mapper.MerchantUserMapper;
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
 * Integration tests for the {@link MerchantUserResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MerchantUserResourceIT {

    @Autowired
    private MerchantUserRepository merchantUserRepository;

    @Autowired
    private MerchantUserMapper merchantUserMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMerchantUserMockMvc;

    private MerchantUser merchantUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MerchantUser createEntity(EntityManager em) {
        MerchantUser merchantUser = new MerchantUser();
        return merchantUser;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MerchantUser createUpdatedEntity(EntityManager em) {
        MerchantUser merchantUser = new MerchantUser();
        return merchantUser;
    }

    @BeforeEach
    public void initTest() {
        merchantUser = createEntity(em);
    }

    @Test
    @Transactional
    void createMerchantUser() throws Exception {
        int databaseSizeBeforeCreate = merchantUserRepository.findAll().size();
        // Create the MerchantUser
        MerchantUserDTO merchantUserDTO = merchantUserMapper.toDto(merchantUser);
        restMerchantUserMockMvc
            .perform(
                post("/api/merchant-users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(merchantUserDTO))
            )
            .andExpect(status().isCreated());

        // Validate the MerchantUser in the database
        List<MerchantUser> merchantUserList = merchantUserRepository.findAll();
        assertThat(merchantUserList).hasSize(databaseSizeBeforeCreate + 1);
        MerchantUser testMerchantUser = merchantUserList.get(merchantUserList.size() - 1);
    }

    @Test
    @Transactional
    void createMerchantUserWithExistingId() throws Exception {
        // Create the MerchantUser with an existing ID
        merchantUser.setId(1L);
        MerchantUserDTO merchantUserDTO = merchantUserMapper.toDto(merchantUser);

        int databaseSizeBeforeCreate = merchantUserRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMerchantUserMockMvc
            .perform(
                post("/api/merchant-users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(merchantUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MerchantUser in the database
        List<MerchantUser> merchantUserList = merchantUserRepository.findAll();
        assertThat(merchantUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMerchantUsers() throws Exception {
        // Initialize the database
        merchantUserRepository.saveAndFlush(merchantUser);

        // Get all the merchantUserList
        restMerchantUserMockMvc
            .perform(get("/api/merchant-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(merchantUser.getId().intValue())));
    }

    @Test
    @Transactional
    void getMerchantUser() throws Exception {
        // Initialize the database
        merchantUserRepository.saveAndFlush(merchantUser);

        // Get the merchantUser
        restMerchantUserMockMvc
            .perform(get("/api/merchant-users/{id}", merchantUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(merchantUser.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingMerchantUser() throws Exception {
        // Get the merchantUser
        restMerchantUserMockMvc.perform(get("/api/merchant-users/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void updateMerchantUser() throws Exception {
        // Initialize the database
        merchantUserRepository.saveAndFlush(merchantUser);

        int databaseSizeBeforeUpdate = merchantUserRepository.findAll().size();

        // Update the merchantUser
        MerchantUser updatedMerchantUser = merchantUserRepository.findById(merchantUser.getId()).get();
        // Disconnect from session so that the updates on updatedMerchantUser are not directly saved in db
        em.detach(updatedMerchantUser);
        MerchantUserDTO merchantUserDTO = merchantUserMapper.toDto(updatedMerchantUser);

        restMerchantUserMockMvc
            .perform(
                put("/api/merchant-users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(merchantUserDTO))
            )
            .andExpect(status().isOk());

        // Validate the MerchantUser in the database
        List<MerchantUser> merchantUserList = merchantUserRepository.findAll();
        assertThat(merchantUserList).hasSize(databaseSizeBeforeUpdate);
        MerchantUser testMerchantUser = merchantUserList.get(merchantUserList.size() - 1);
    }

    @Test
    @Transactional
    void updateNonExistingMerchantUser() throws Exception {
        int databaseSizeBeforeUpdate = merchantUserRepository.findAll().size();

        // Create the MerchantUser
        MerchantUserDTO merchantUserDTO = merchantUserMapper.toDto(merchantUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMerchantUserMockMvc
            .perform(
                put("/api/merchant-users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(merchantUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MerchantUser in the database
        List<MerchantUser> merchantUserList = merchantUserRepository.findAll();
        assertThat(merchantUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMerchantUserWithPatch() throws Exception {
        // Initialize the database
        merchantUserRepository.saveAndFlush(merchantUser);

        int databaseSizeBeforeUpdate = merchantUserRepository.findAll().size();

        // Update the merchantUser using partial update
        MerchantUser partialUpdatedMerchantUser = new MerchantUser();
        partialUpdatedMerchantUser.setId(merchantUser.getId());

        restMerchantUserMockMvc
            .perform(
                patch("/api/merchant-users")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMerchantUser))
            )
            .andExpect(status().isOk());

        // Validate the MerchantUser in the database
        List<MerchantUser> merchantUserList = merchantUserRepository.findAll();
        assertThat(merchantUserList).hasSize(databaseSizeBeforeUpdate);
        MerchantUser testMerchantUser = merchantUserList.get(merchantUserList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateMerchantUserWithPatch() throws Exception {
        // Initialize the database
        merchantUserRepository.saveAndFlush(merchantUser);

        int databaseSizeBeforeUpdate = merchantUserRepository.findAll().size();

        // Update the merchantUser using partial update
        MerchantUser partialUpdatedMerchantUser = new MerchantUser();
        partialUpdatedMerchantUser.setId(merchantUser.getId());

        restMerchantUserMockMvc
            .perform(
                patch("/api/merchant-users")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMerchantUser))
            )
            .andExpect(status().isOk());

        // Validate the MerchantUser in the database
        List<MerchantUser> merchantUserList = merchantUserRepository.findAll();
        assertThat(merchantUserList).hasSize(databaseSizeBeforeUpdate);
        MerchantUser testMerchantUser = merchantUserList.get(merchantUserList.size() - 1);
    }

    @Test
    @Transactional
    void partialUpdateMerchantUserShouldThrown() throws Exception {
        // Update the merchantUser without id should throw
        MerchantUser partialUpdatedMerchantUser = new MerchantUser();

        restMerchantUserMockMvc
            .perform(
                patch("/api/merchant-users")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMerchantUser))
            )
            .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void deleteMerchantUser() throws Exception {
        // Initialize the database
        merchantUserRepository.saveAndFlush(merchantUser);

        int databaseSizeBeforeDelete = merchantUserRepository.findAll().size();

        // Delete the merchantUser
        restMerchantUserMockMvc
            .perform(delete("/api/merchant-users/{id}", merchantUser.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MerchantUser> merchantUserList = merchantUserRepository.findAll();
        assertThat(merchantUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
