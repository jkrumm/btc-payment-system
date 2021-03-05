package com.jkrumm.btcpay.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jkrumm.btcpay.IntegrationTest;
import com.jkrumm.btcpay.domain.Transaction;
import com.jkrumm.btcpay.domain.enumeration.TransactionType;
import com.jkrumm.btcpay.repository.TransactionRepository;
import com.jkrumm.btcpay.service.dto.TransactionDTO;
import com.jkrumm.btcpay.service.mapper.TransactionMapper;
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
 * Integration tests for the {@link TransactionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TransactionResourceIT {

    private static final Instant DEFAULT_INITIATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INITIATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final TransactionType DEFAULT_TRANSACTION_TYPE = TransactionType.INCOMING_UNKNOWN;
    private static final TransactionType UPDATED_TRANSACTION_TYPE = TransactionType.INCOMING_CUSTOMER;

    private static final String DEFAULT_TX_HASH = "AAAAAAAAAA";
    private static final String UPDATED_TX_HASH = "BBBBBBBBBB";

    private static final Long DEFAULT_EXPECTED_AMOUNT = 1L;
    private static final Long UPDATED_EXPECTED_AMOUNT = 2L;

    private static final Long DEFAULT_ACTUAL_AMOUNT = 1L;
    private static final Long UPDATED_ACTUAL_AMOUNT = 2L;

    private static final Long DEFAULT_TRANSACTION_FEE = 1L;
    private static final Long UPDATED_TRANSACTION_FEE = 2L;

    private static final Long DEFAULT_SERVICE_FEE = 1L;
    private static final Long UPDATED_SERVICE_FEE = 2L;

    private static final Double DEFAULT_BTC_USD = 1D;
    private static final Double UPDATED_BTC_USD = 2D;

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTransactionMockMvc;

    private Transaction transaction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transaction createEntity(EntityManager em) {
        Transaction transaction = new Transaction()
            .initiatedAt(DEFAULT_INITIATED_AT)
            .transactionType(DEFAULT_TRANSACTION_TYPE)
            .txHash(DEFAULT_TX_HASH)
            .expectedAmount(DEFAULT_EXPECTED_AMOUNT)
            .actualAmount(DEFAULT_ACTUAL_AMOUNT)
            .transactionFee(DEFAULT_TRANSACTION_FEE)
            .serviceFee(DEFAULT_SERVICE_FEE)
            .btcUsd(DEFAULT_BTC_USD)
            .address(DEFAULT_ADDRESS)
            .amount(DEFAULT_AMOUNT);
        return transaction;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transaction createUpdatedEntity(EntityManager em) {
        Transaction transaction = new Transaction()
            .initiatedAt(UPDATED_INITIATED_AT)
            .transactionType(UPDATED_TRANSACTION_TYPE)
            .txHash(UPDATED_TX_HASH)
            .expectedAmount(UPDATED_EXPECTED_AMOUNT)
            .actualAmount(UPDATED_ACTUAL_AMOUNT)
            .transactionFee(UPDATED_TRANSACTION_FEE)
            .serviceFee(UPDATED_SERVICE_FEE)
            .btcUsd(UPDATED_BTC_USD)
            .address(UPDATED_ADDRESS)
            .amount(UPDATED_AMOUNT);
        return transaction;
    }

    @BeforeEach
    public void initTest() {
        transaction = createEntity(em);
    }

    @Test
    @Transactional
    void createTransaction() throws Exception {
        int databaseSizeBeforeCreate = transactionRepository.findAll().size();
        // Create the Transaction
        TransactionDTO transactionDTO = transactionMapper.toDto(transaction);
        restTransactionMockMvc
            .perform(
                post("/api/transactions").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transactionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeCreate + 1);
        Transaction testTransaction = transactionList.get(transactionList.size() - 1);
        assertThat(testTransaction.getInitiatedAt()).isEqualTo(DEFAULT_INITIATED_AT);
        assertThat(testTransaction.getTransactionType()).isEqualTo(DEFAULT_TRANSACTION_TYPE);
        assertThat(testTransaction.getTxHash()).isEqualTo(DEFAULT_TX_HASH);
        assertThat(testTransaction.getExpectedAmount()).isEqualTo(DEFAULT_EXPECTED_AMOUNT);
        assertThat(testTransaction.getActualAmount()).isEqualTo(DEFAULT_ACTUAL_AMOUNT);
        assertThat(testTransaction.getTransactionFee()).isEqualTo(DEFAULT_TRANSACTION_FEE);
        assertThat(testTransaction.getServiceFee()).isEqualTo(DEFAULT_SERVICE_FEE);
        assertThat(testTransaction.getBtcUsd()).isEqualTo(DEFAULT_BTC_USD);
        assertThat(testTransaction.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testTransaction.getAmount()).isEqualTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    void createTransactionWithExistingId() throws Exception {
        // Create the Transaction with an existing ID
        transaction.setId(1L);
        TransactionDTO transactionDTO = transactionMapper.toDto(transaction);

        int databaseSizeBeforeCreate = transactionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransactionMockMvc
            .perform(
                post("/api/transactions").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkInitiatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionRepository.findAll().size();
        // set the field null
        transaction.setInitiatedAt(null);

        // Create the Transaction, which fails.
        TransactionDTO transactionDTO = transactionMapper.toDto(transaction);

        restTransactionMockMvc
            .perform(
                post("/api/transactions").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transactionDTO))
            )
            .andExpect(status().isBadRequest());

        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTransactions() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList
        restTransactionMockMvc
            .perform(get("/api/transactions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].initiatedAt").value(hasItem(DEFAULT_INITIATED_AT.toString())))
            .andExpect(jsonPath("$.[*].transactionType").value(hasItem(DEFAULT_TRANSACTION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].txHash").value(hasItem(DEFAULT_TX_HASH)))
            .andExpect(jsonPath("$.[*].expectedAmount").value(hasItem(DEFAULT_EXPECTED_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].actualAmount").value(hasItem(DEFAULT_ACTUAL_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].transactionFee").value(hasItem(DEFAULT_TRANSACTION_FEE.intValue())))
            .andExpect(jsonPath("$.[*].serviceFee").value(hasItem(DEFAULT_SERVICE_FEE.intValue())))
            .andExpect(jsonPath("$.[*].btcUsd").value(hasItem(DEFAULT_BTC_USD.doubleValue())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())));
    }

    @Test
    @Transactional
    void getTransaction() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get the transaction
        restTransactionMockMvc
            .perform(get("/api/transactions/{id}", transaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(transaction.getId().intValue()))
            .andExpect(jsonPath("$.initiatedAt").value(DEFAULT_INITIATED_AT.toString()))
            .andExpect(jsonPath("$.transactionType").value(DEFAULT_TRANSACTION_TYPE.toString()))
            .andExpect(jsonPath("$.txHash").value(DEFAULT_TX_HASH))
            .andExpect(jsonPath("$.expectedAmount").value(DEFAULT_EXPECTED_AMOUNT.intValue()))
            .andExpect(jsonPath("$.actualAmount").value(DEFAULT_ACTUAL_AMOUNT.intValue()))
            .andExpect(jsonPath("$.transactionFee").value(DEFAULT_TRANSACTION_FEE.intValue()))
            .andExpect(jsonPath("$.serviceFee").value(DEFAULT_SERVICE_FEE.intValue()))
            .andExpect(jsonPath("$.btcUsd").value(DEFAULT_BTC_USD.doubleValue()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingTransaction() throws Exception {
        // Get the transaction
        restTransactionMockMvc.perform(get("/api/transactions/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void updateTransaction() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        int databaseSizeBeforeUpdate = transactionRepository.findAll().size();

        // Update the transaction
        Transaction updatedTransaction = transactionRepository.findById(transaction.getId()).get();
        // Disconnect from session so that the updates on updatedTransaction are not directly saved in db
        em.detach(updatedTransaction);
        updatedTransaction
            .initiatedAt(UPDATED_INITIATED_AT)
            .transactionType(UPDATED_TRANSACTION_TYPE)
            .txHash(UPDATED_TX_HASH)
            .expectedAmount(UPDATED_EXPECTED_AMOUNT)
            .actualAmount(UPDATED_ACTUAL_AMOUNT)
            .transactionFee(UPDATED_TRANSACTION_FEE)
            .serviceFee(UPDATED_SERVICE_FEE)
            .btcUsd(UPDATED_BTC_USD)
            .address(UPDATED_ADDRESS)
            .amount(UPDATED_AMOUNT);
        TransactionDTO transactionDTO = transactionMapper.toDto(updatedTransaction);

        restTransactionMockMvc
            .perform(
                put("/api/transactions").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transactionDTO))
            )
            .andExpect(status().isOk());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeUpdate);
        Transaction testTransaction = transactionList.get(transactionList.size() - 1);
        assertThat(testTransaction.getInitiatedAt()).isEqualTo(UPDATED_INITIATED_AT);
        assertThat(testTransaction.getTransactionType()).isEqualTo(UPDATED_TRANSACTION_TYPE);
        assertThat(testTransaction.getTxHash()).isEqualTo(UPDATED_TX_HASH);
        assertThat(testTransaction.getExpectedAmount()).isEqualTo(UPDATED_EXPECTED_AMOUNT);
        assertThat(testTransaction.getActualAmount()).isEqualTo(UPDATED_ACTUAL_AMOUNT);
        assertThat(testTransaction.getTransactionFee()).isEqualTo(UPDATED_TRANSACTION_FEE);
        assertThat(testTransaction.getServiceFee()).isEqualTo(UPDATED_SERVICE_FEE);
        assertThat(testTransaction.getBtcUsd()).isEqualTo(UPDATED_BTC_USD);
        assertThat(testTransaction.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testTransaction.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void updateNonExistingTransaction() throws Exception {
        int databaseSizeBeforeUpdate = transactionRepository.findAll().size();

        // Create the Transaction
        TransactionDTO transactionDTO = transactionMapper.toDto(transaction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransactionMockMvc
            .perform(
                put("/api/transactions").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTransactionWithPatch() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        int databaseSizeBeforeUpdate = transactionRepository.findAll().size();

        // Update the transaction using partial update
        Transaction partialUpdatedTransaction = new Transaction();
        partialUpdatedTransaction.setId(transaction.getId());

        partialUpdatedTransaction.transactionType(UPDATED_TRANSACTION_TYPE).btcUsd(UPDATED_BTC_USD);

        restTransactionMockMvc
            .perform(
                patch("/api/transactions")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTransaction))
            )
            .andExpect(status().isOk());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeUpdate);
        Transaction testTransaction = transactionList.get(transactionList.size() - 1);
        assertThat(testTransaction.getInitiatedAt()).isEqualTo(DEFAULT_INITIATED_AT);
        assertThat(testTransaction.getTransactionType()).isEqualTo(UPDATED_TRANSACTION_TYPE);
        assertThat(testTransaction.getTxHash()).isEqualTo(DEFAULT_TX_HASH);
        assertThat(testTransaction.getExpectedAmount()).isEqualTo(DEFAULT_EXPECTED_AMOUNT);
        assertThat(testTransaction.getActualAmount()).isEqualTo(DEFAULT_ACTUAL_AMOUNT);
        assertThat(testTransaction.getTransactionFee()).isEqualTo(DEFAULT_TRANSACTION_FEE);
        assertThat(testTransaction.getServiceFee()).isEqualTo(DEFAULT_SERVICE_FEE);
        assertThat(testTransaction.getBtcUsd()).isEqualTo(UPDATED_BTC_USD);
        assertThat(testTransaction.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testTransaction.getAmount()).isEqualTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    void fullUpdateTransactionWithPatch() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        int databaseSizeBeforeUpdate = transactionRepository.findAll().size();

        // Update the transaction using partial update
        Transaction partialUpdatedTransaction = new Transaction();
        partialUpdatedTransaction.setId(transaction.getId());

        partialUpdatedTransaction
            .initiatedAt(UPDATED_INITIATED_AT)
            .transactionType(UPDATED_TRANSACTION_TYPE)
            .txHash(UPDATED_TX_HASH)
            .expectedAmount(UPDATED_EXPECTED_AMOUNT)
            .actualAmount(UPDATED_ACTUAL_AMOUNT)
            .transactionFee(UPDATED_TRANSACTION_FEE)
            .serviceFee(UPDATED_SERVICE_FEE)
            .btcUsd(UPDATED_BTC_USD)
            .address(UPDATED_ADDRESS)
            .amount(UPDATED_AMOUNT);

        restTransactionMockMvc
            .perform(
                patch("/api/transactions")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTransaction))
            )
            .andExpect(status().isOk());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeUpdate);
        Transaction testTransaction = transactionList.get(transactionList.size() - 1);
        assertThat(testTransaction.getInitiatedAt()).isEqualTo(UPDATED_INITIATED_AT);
        assertThat(testTransaction.getTransactionType()).isEqualTo(UPDATED_TRANSACTION_TYPE);
        assertThat(testTransaction.getTxHash()).isEqualTo(UPDATED_TX_HASH);
        assertThat(testTransaction.getExpectedAmount()).isEqualTo(UPDATED_EXPECTED_AMOUNT);
        assertThat(testTransaction.getActualAmount()).isEqualTo(UPDATED_ACTUAL_AMOUNT);
        assertThat(testTransaction.getTransactionFee()).isEqualTo(UPDATED_TRANSACTION_FEE);
        assertThat(testTransaction.getServiceFee()).isEqualTo(UPDATED_SERVICE_FEE);
        assertThat(testTransaction.getBtcUsd()).isEqualTo(UPDATED_BTC_USD);
        assertThat(testTransaction.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testTransaction.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void partialUpdateTransactionShouldThrown() throws Exception {
        // Update the transaction without id should throw
        Transaction partialUpdatedTransaction = new Transaction();

        restTransactionMockMvc
            .perform(
                patch("/api/transactions")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTransaction))
            )
            .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void deleteTransaction() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        int databaseSizeBeforeDelete = transactionRepository.findAll().size();

        // Delete the transaction
        restTransactionMockMvc
            .perform(delete("/api/transactions/{id}", transaction.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
