package com.jkrumm.btcpay.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jkrumm.btcpay.BtcPaymentSystemApp;
import com.jkrumm.btcpay.domain.Transaction;
import com.jkrumm.btcpay.domain.enumeration.TransactionType;
import com.jkrumm.btcpay.repository.TransactionRepository;
import com.jkrumm.btcpay.service.TransactionService;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TransactionResource} REST controller.
 */
@SpringBootTest(classes = BtcPaymentSystemApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TransactionResourceIT {
    private static final Instant DEFAULT_INITIATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INITIATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final TransactionType DEFAULT_TRANSACTION_TYPE = TransactionType.INCOMING_UNKNOWN;
    private static final TransactionType UPDATED_TRANSACTION_TYPE = TransactionType.INCOMING_CUSTOMER;

    private static final Boolean DEFAULT_IS_MEMPOOL = false;
    private static final Boolean UPDATED_IS_MEMPOOL = true;

    private static final String DEFAULT_TX_HASH = "AAAAAAAAAA";
    private static final String UPDATED_TX_HASH = "BBBBBBBBBB";

    private static final String DEFAULT_FROM_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_FROM_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_TO_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_TO_ADDRESS = "BBBBBBBBBB";

    private static final Long DEFAULT_EXPECTED_AMOUNT = 1L;
    private static final Long UPDATED_EXPECTED_AMOUNT = 2L;

    private static final Long DEFAULT_AMOUNT = 1L;
    private static final Long UPDATED_AMOUNT = 2L;

    private static final Long DEFAULT_SERVICE_FEE = 1L;
    private static final Long UPDATED_SERVICE_FEE = 2L;

    private static final Long DEFAULT_BTC_PRICE = 1L;
    private static final Long UPDATED_BTC_PRICE = 2L;

    private static final Boolean DEFAULT_IS_WITHDRAWED = false;
    private static final Boolean UPDATED_IS_WITHDRAWED = true;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private TransactionService transactionService;

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
            .isMempool(DEFAULT_IS_MEMPOOL)
            .txHash(DEFAULT_TX_HASH)
            .fromAddress(DEFAULT_FROM_ADDRESS)
            .toAddress(DEFAULT_TO_ADDRESS)
            .expectedAmount(DEFAULT_EXPECTED_AMOUNT)
            .amount(DEFAULT_AMOUNT)
            .serviceFee(DEFAULT_SERVICE_FEE)
            .btcPrice(DEFAULT_BTC_PRICE)
            .isWithdrawed(DEFAULT_IS_WITHDRAWED);
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
            .isMempool(UPDATED_IS_MEMPOOL)
            .txHash(UPDATED_TX_HASH)
            .fromAddress(UPDATED_FROM_ADDRESS)
            .toAddress(UPDATED_TO_ADDRESS)
            .expectedAmount(UPDATED_EXPECTED_AMOUNT)
            .amount(UPDATED_AMOUNT)
            .serviceFee(UPDATED_SERVICE_FEE)
            .btcPrice(UPDATED_BTC_PRICE)
            .isWithdrawed(UPDATED_IS_WITHDRAWED);
        return transaction;
    }

    @BeforeEach
    public void initTest() {
        transaction = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransaction() throws Exception {
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
        assertThat(testTransaction.isIsMempool()).isEqualTo(DEFAULT_IS_MEMPOOL);
        assertThat(testTransaction.getTxHash()).isEqualTo(DEFAULT_TX_HASH);
        assertThat(testTransaction.getFromAddress()).isEqualTo(DEFAULT_FROM_ADDRESS);
        assertThat(testTransaction.getToAddress()).isEqualTo(DEFAULT_TO_ADDRESS);
        assertThat(testTransaction.getExpectedAmount()).isEqualTo(DEFAULT_EXPECTED_AMOUNT);
        assertThat(testTransaction.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testTransaction.getServiceFee()).isEqualTo(DEFAULT_SERVICE_FEE);
        assertThat(testTransaction.getBtcPrice()).isEqualTo(DEFAULT_BTC_PRICE);
        assertThat(testTransaction.isIsWithdrawed()).isEqualTo(DEFAULT_IS_WITHDRAWED);
    }

    @Test
    @Transactional
    public void createTransactionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transactionRepository.findAll().size();

        // Create the Transaction with an existing ID
        transaction.setId(1L);
        TransactionDTO transactionDTO = transactionMapper.toDto(transaction);

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
    public void checkInitiatedAtIsRequired() throws Exception {
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
    public void checkTransactionTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionRepository.findAll().size();
        // set the field null
        transaction.setTransactionType(null);

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
    public void checkIsMempoolIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionRepository.findAll().size();
        // set the field null
        transaction.setIsMempool(null);

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
    public void checkToAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionRepository.findAll().size();
        // set the field null
        transaction.setToAddress(null);

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
    public void checkExpectedAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionRepository.findAll().size();
        // set the field null
        transaction.setExpectedAmount(null);

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
    public void checkServiceFeeIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionRepository.findAll().size();
        // set the field null
        transaction.setServiceFee(null);

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
    public void checkBtcPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionRepository.findAll().size();
        // set the field null
        transaction.setBtcPrice(null);

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
    public void checkIsWithdrawedIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionRepository.findAll().size();
        // set the field null
        transaction.setIsWithdrawed(null);

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
    public void getAllTransactions() throws Exception {
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
            .andExpect(jsonPath("$.[*].isMempool").value(hasItem(DEFAULT_IS_MEMPOOL.booleanValue())))
            .andExpect(jsonPath("$.[*].txHash").value(hasItem(DEFAULT_TX_HASH)))
            .andExpect(jsonPath("$.[*].fromAddress").value(hasItem(DEFAULT_FROM_ADDRESS)))
            .andExpect(jsonPath("$.[*].toAddress").value(hasItem(DEFAULT_TO_ADDRESS)))
            .andExpect(jsonPath("$.[*].expectedAmount").value(hasItem(DEFAULT_EXPECTED_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].serviceFee").value(hasItem(DEFAULT_SERVICE_FEE.intValue())))
            .andExpect(jsonPath("$.[*].btcPrice").value(hasItem(DEFAULT_BTC_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].isWithdrawed").value(hasItem(DEFAULT_IS_WITHDRAWED.booleanValue())));
    }

    @Test
    @Transactional
    public void getTransaction() throws Exception {
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
            .andExpect(jsonPath("$.isMempool").value(DEFAULT_IS_MEMPOOL.booleanValue()))
            .andExpect(jsonPath("$.txHash").value(DEFAULT_TX_HASH))
            .andExpect(jsonPath("$.fromAddress").value(DEFAULT_FROM_ADDRESS))
            .andExpect(jsonPath("$.toAddress").value(DEFAULT_TO_ADDRESS))
            .andExpect(jsonPath("$.expectedAmount").value(DEFAULT_EXPECTED_AMOUNT.intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.serviceFee").value(DEFAULT_SERVICE_FEE.intValue()))
            .andExpect(jsonPath("$.btcPrice").value(DEFAULT_BTC_PRICE.intValue()))
            .andExpect(jsonPath("$.isWithdrawed").value(DEFAULT_IS_WITHDRAWED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTransaction() throws Exception {
        // Get the transaction
        restTransactionMockMvc.perform(get("/api/transactions/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransaction() throws Exception {
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
            .isMempool(UPDATED_IS_MEMPOOL)
            .txHash(UPDATED_TX_HASH)
            .fromAddress(UPDATED_FROM_ADDRESS)
            .toAddress(UPDATED_TO_ADDRESS)
            .expectedAmount(UPDATED_EXPECTED_AMOUNT)
            .amount(UPDATED_AMOUNT)
            .serviceFee(UPDATED_SERVICE_FEE)
            .btcPrice(UPDATED_BTC_PRICE)
            .isWithdrawed(UPDATED_IS_WITHDRAWED);
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
        assertThat(testTransaction.isIsMempool()).isEqualTo(UPDATED_IS_MEMPOOL);
        assertThat(testTransaction.getTxHash()).isEqualTo(UPDATED_TX_HASH);
        assertThat(testTransaction.getFromAddress()).isEqualTo(UPDATED_FROM_ADDRESS);
        assertThat(testTransaction.getToAddress()).isEqualTo(UPDATED_TO_ADDRESS);
        assertThat(testTransaction.getExpectedAmount()).isEqualTo(UPDATED_EXPECTED_AMOUNT);
        assertThat(testTransaction.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testTransaction.getServiceFee()).isEqualTo(UPDATED_SERVICE_FEE);
        assertThat(testTransaction.getBtcPrice()).isEqualTo(UPDATED_BTC_PRICE);
        assertThat(testTransaction.isIsWithdrawed()).isEqualTo(UPDATED_IS_WITHDRAWED);
    }

    @Test
    @Transactional
    public void updateNonExistingTransaction() throws Exception {
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
    public void deleteTransaction() throws Exception {
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
