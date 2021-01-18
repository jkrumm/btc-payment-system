package com.jkrumm.btcpay.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jkrumm.btcpay.BtcPaymentSystemApp;
import com.jkrumm.btcpay.domain.Block;
import com.jkrumm.btcpay.repository.BlockRepository;
import com.jkrumm.btcpay.service.BlockService;
import com.jkrumm.btcpay.service.dto.BlockDTO;
import com.jkrumm.btcpay.service.mapper.BlockMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link BlockResource} REST controller.
 */
@SpringBootTest(classes = BtcPaymentSystemApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class BlockResourceIT {
    private static final Instant DEFAULT_MINED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MINED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_BLOCK_HEIGHT = 1L;
    private static final Long UPDATED_BLOCK_HEIGHT = 2L;

    private static final String DEFAULT_BLOCK_HASH = "AAAAAAAAAA";
    private static final String UPDATED_BLOCK_HASH = "BBBBBBBBBB";

    private static final Long DEFAULT_AVAILABLE = 1L;
    private static final Long UPDATED_AVAILABLE = 2L;

    private static final Long DEFAULT_ESTIMATED = 1L;
    private static final Long UPDATED_ESTIMATED = 2L;

    private static final Long DEFAULT_AVAILABLE_SPENDABLE = 1L;
    private static final Long UPDATED_AVAILABLE_SPENDABLE = 2L;

    private static final Long DEFAULT_ESTIMATED_SPENDABLE = 1L;
    private static final Long UPDATED_ESTIMATED_SPENDABLE = 2L;

    @Autowired
    private BlockRepository blockRepository;

    @Autowired
    private BlockMapper blockMapper;

    @Autowired
    private BlockService blockService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBlockMockMvc;

    private Block block;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Block createEntity(EntityManager em) {
        Block block = new Block()
            .minedAt(DEFAULT_MINED_AT)
            .blockHeight(DEFAULT_BLOCK_HEIGHT)
            .blockHash(DEFAULT_BLOCK_HASH)
            .available(DEFAULT_AVAILABLE)
            .estimated(DEFAULT_ESTIMATED)
            .availableSpendable(DEFAULT_AVAILABLE_SPENDABLE)
            .estimatedSpendable(DEFAULT_ESTIMATED_SPENDABLE);
        return block;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Block createUpdatedEntity(EntityManager em) {
        Block block = new Block()
            .minedAt(UPDATED_MINED_AT)
            .blockHeight(UPDATED_BLOCK_HEIGHT)
            .blockHash(UPDATED_BLOCK_HASH)
            .available(UPDATED_AVAILABLE)
            .estimated(UPDATED_ESTIMATED)
            .availableSpendable(UPDATED_AVAILABLE_SPENDABLE)
            .estimatedSpendable(UPDATED_ESTIMATED_SPENDABLE);
        return block;
    }

    @BeforeEach
    public void initTest() {
        block = createEntity(em);
    }

    @Test
    @Transactional
    public void createBlock() throws Exception {
        int databaseSizeBeforeCreate = blockRepository.findAll().size();
        // Create the Block
        BlockDTO blockDTO = blockMapper.toDto(block);
        restBlockMockMvc
            .perform(post("/api/blocks").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(blockDTO)))
            .andExpect(status().isCreated());

        // Validate the Block in the database
        List<Block> blockList = blockRepository.findAll();
        assertThat(blockList).hasSize(databaseSizeBeforeCreate + 1);
        Block testBlock = blockList.get(blockList.size() - 1);
        assertThat(testBlock.getMinedAt()).isEqualTo(DEFAULT_MINED_AT);
        assertThat(testBlock.getBlockHeight()).isEqualTo(DEFAULT_BLOCK_HEIGHT);
        assertThat(testBlock.getBlockHash()).isEqualTo(DEFAULT_BLOCK_HASH);
        assertThat(testBlock.getAvailable()).isEqualTo(DEFAULT_AVAILABLE);
        assertThat(testBlock.getEstimated()).isEqualTo(DEFAULT_ESTIMATED);
        assertThat(testBlock.getAvailableSpendable()).isEqualTo(DEFAULT_AVAILABLE_SPENDABLE);
        assertThat(testBlock.getEstimatedSpendable()).isEqualTo(DEFAULT_ESTIMATED_SPENDABLE);
    }

    @Test
    @Transactional
    public void createBlockWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = blockRepository.findAll().size();

        // Create the Block with an existing ID
        block.setId(1L);
        BlockDTO blockDTO = blockMapper.toDto(block);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBlockMockMvc
            .perform(post("/api/blocks").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(blockDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Block in the database
        List<Block> blockList = blockRepository.findAll();
        assertThat(blockList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkBlockHeightIsRequired() throws Exception {
        int databaseSizeBeforeTest = blockRepository.findAll().size();
        // set the field null
        block.setBlockHeight(null);

        // Create the Block, which fails.
        BlockDTO blockDTO = blockMapper.toDto(block);

        restBlockMockMvc
            .perform(post("/api/blocks").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(blockDTO)))
            .andExpect(status().isBadRequest());

        List<Block> blockList = blockRepository.findAll();
        assertThat(blockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBlockHashIsRequired() throws Exception {
        int databaseSizeBeforeTest = blockRepository.findAll().size();
        // set the field null
        block.setBlockHash(null);

        // Create the Block, which fails.
        BlockDTO blockDTO = blockMapper.toDto(block);

        restBlockMockMvc
            .perform(post("/api/blocks").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(blockDTO)))
            .andExpect(status().isBadRequest());

        List<Block> blockList = blockRepository.findAll();
        assertThat(blockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAvailableIsRequired() throws Exception {
        int databaseSizeBeforeTest = blockRepository.findAll().size();
        // set the field null
        block.setAvailable(null);

        // Create the Block, which fails.
        BlockDTO blockDTO = blockMapper.toDto(block);

        restBlockMockMvc
            .perform(post("/api/blocks").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(blockDTO)))
            .andExpect(status().isBadRequest());

        List<Block> blockList = blockRepository.findAll();
        assertThat(blockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEstimatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = blockRepository.findAll().size();
        // set the field null
        block.setEstimated(null);

        // Create the Block, which fails.
        BlockDTO blockDTO = blockMapper.toDto(block);

        restBlockMockMvc
            .perform(post("/api/blocks").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(blockDTO)))
            .andExpect(status().isBadRequest());

        List<Block> blockList = blockRepository.findAll();
        assertThat(blockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAvailableSpendableIsRequired() throws Exception {
        int databaseSizeBeforeTest = blockRepository.findAll().size();
        // set the field null
        block.setAvailableSpendable(null);

        // Create the Block, which fails.
        BlockDTO blockDTO = blockMapper.toDto(block);

        restBlockMockMvc
            .perform(post("/api/blocks").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(blockDTO)))
            .andExpect(status().isBadRequest());

        List<Block> blockList = blockRepository.findAll();
        assertThat(blockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEstimatedSpendableIsRequired() throws Exception {
        int databaseSizeBeforeTest = blockRepository.findAll().size();
        // set the field null
        block.setEstimatedSpendable(null);

        // Create the Block, which fails.
        BlockDTO blockDTO = blockMapper.toDto(block);

        restBlockMockMvc
            .perform(post("/api/blocks").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(blockDTO)))
            .andExpect(status().isBadRequest());

        List<Block> blockList = blockRepository.findAll();
        assertThat(blockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBlocks() throws Exception {
        // Initialize the database
        blockRepository.saveAndFlush(block);

        // Get all the blockList
        restBlockMockMvc
            .perform(get("/api/blocks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(block.getId().intValue())))
            .andExpect(jsonPath("$.[*].minedAt").value(hasItem(DEFAULT_MINED_AT.toString())))
            .andExpect(jsonPath("$.[*].blockHeight").value(hasItem(DEFAULT_BLOCK_HEIGHT.intValue())))
            .andExpect(jsonPath("$.[*].blockHash").value(hasItem(DEFAULT_BLOCK_HASH)))
            .andExpect(jsonPath("$.[*].available").value(hasItem(DEFAULT_AVAILABLE.intValue())))
            .andExpect(jsonPath("$.[*].estimated").value(hasItem(DEFAULT_ESTIMATED.intValue())))
            .andExpect(jsonPath("$.[*].availableSpendable").value(hasItem(DEFAULT_AVAILABLE_SPENDABLE.intValue())))
            .andExpect(jsonPath("$.[*].estimatedSpendable").value(hasItem(DEFAULT_ESTIMATED_SPENDABLE.intValue())));
    }

    @Test
    @Transactional
    public void getBlock() throws Exception {
        // Initialize the database
        blockRepository.saveAndFlush(block);

        // Get the block
        restBlockMockMvc
            .perform(get("/api/blocks/{id}", block.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(block.getId().intValue()))
            .andExpect(jsonPath("$.minedAt").value(DEFAULT_MINED_AT.toString()))
            .andExpect(jsonPath("$.blockHeight").value(DEFAULT_BLOCK_HEIGHT.intValue()))
            .andExpect(jsonPath("$.blockHash").value(DEFAULT_BLOCK_HASH))
            .andExpect(jsonPath("$.available").value(DEFAULT_AVAILABLE.intValue()))
            .andExpect(jsonPath("$.estimated").value(DEFAULT_ESTIMATED.intValue()))
            .andExpect(jsonPath("$.availableSpendable").value(DEFAULT_AVAILABLE_SPENDABLE.intValue()))
            .andExpect(jsonPath("$.estimatedSpendable").value(DEFAULT_ESTIMATED_SPENDABLE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBlock() throws Exception {
        // Get the block
        restBlockMockMvc.perform(get("/api/blocks/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBlock() throws Exception {
        // Initialize the database
        blockRepository.saveAndFlush(block);

        int databaseSizeBeforeUpdate = blockRepository.findAll().size();

        // Update the block
        Block updatedBlock = blockRepository.findById(block.getId()).get();
        // Disconnect from session so that the updates on updatedBlock are not directly saved in db
        em.detach(updatedBlock);
        updatedBlock
            .minedAt(UPDATED_MINED_AT)
            .blockHeight(UPDATED_BLOCK_HEIGHT)
            .blockHash(UPDATED_BLOCK_HASH)
            .available(UPDATED_AVAILABLE)
            .estimated(UPDATED_ESTIMATED)
            .availableSpendable(UPDATED_AVAILABLE_SPENDABLE)
            .estimatedSpendable(UPDATED_ESTIMATED_SPENDABLE);
        BlockDTO blockDTO = blockMapper.toDto(updatedBlock);

        restBlockMockMvc
            .perform(put("/api/blocks").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(blockDTO)))
            .andExpect(status().isOk());

        // Validate the Block in the database
        List<Block> blockList = blockRepository.findAll();
        assertThat(blockList).hasSize(databaseSizeBeforeUpdate);
        Block testBlock = blockList.get(blockList.size() - 1);
        assertThat(testBlock.getMinedAt()).isEqualTo(UPDATED_MINED_AT);
        assertThat(testBlock.getBlockHeight()).isEqualTo(UPDATED_BLOCK_HEIGHT);
        assertThat(testBlock.getBlockHash()).isEqualTo(UPDATED_BLOCK_HASH);
        assertThat(testBlock.getAvailable()).isEqualTo(UPDATED_AVAILABLE);
        assertThat(testBlock.getEstimated()).isEqualTo(UPDATED_ESTIMATED);
        assertThat(testBlock.getAvailableSpendable()).isEqualTo(UPDATED_AVAILABLE_SPENDABLE);
        assertThat(testBlock.getEstimatedSpendable()).isEqualTo(UPDATED_ESTIMATED_SPENDABLE);
    }

    @Test
    @Transactional
    public void updateNonExistingBlock() throws Exception {
        int databaseSizeBeforeUpdate = blockRepository.findAll().size();

        // Create the Block
        BlockDTO blockDTO = blockMapper.toDto(block);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBlockMockMvc
            .perform(put("/api/blocks").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(blockDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Block in the database
        List<Block> blockList = blockRepository.findAll();
        assertThat(blockList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBlock() throws Exception {
        // Initialize the database
        blockRepository.saveAndFlush(block);

        int databaseSizeBeforeDelete = blockRepository.findAll().size();

        // Delete the block
        restBlockMockMvc
            .perform(delete("/api/blocks/{id}", block.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Block> blockList = blockRepository.findAll();
        assertThat(blockList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
