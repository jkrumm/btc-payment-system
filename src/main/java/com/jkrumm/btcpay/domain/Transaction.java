package com.jkrumm.btcpay.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jkrumm.btcpay.domain.enumeration.TransactionType;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Central table to persist transaction details. All holdings will be derived from it.
 */
@Entity
@Table(name = "transaction")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Transaction initiated at
     */
    @NotNull
    @Column(name = "initiated_at", nullable = false)
    private Instant initiatedAt;

    /**
     * Transaction enum type
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private TransactionType transactionType;

    /**
     * Is Transaction still in the Mempool
     */
    @NotNull
    @Column(name = "is_mempool", nullable = false)
    private Boolean isMempool;

    /**
     * Transaction hash
     */

    @Column(name = "tx_hash", unique = true)
    private String txHash;

    /**
     * Transaction send from address
     */
    @Column(name = "from_address")
    private String fromAddress;

    /**
     * Transaction send to address
     */
    @NotNull
    @Column(name = "to_address", nullable = false)
    private String toAddress;

    /**
     * Expected BTC amount from the customer
     */
    @NotNull
    @Column(name = "expected_amount", nullable = false)
    private Long expectedAmount;

    /**
     * Actual BTC amount of the transaction
     */
    @Column(name = "amount")
    private Long amount;

    /**
     * Service fee
     */
    @NotNull
    @Column(name = "service_fee", nullable = false)
    private Long serviceFee;

    /**
     * BTC price at intiation
     */
    @NotNull
    @Column(name = "btc_price", nullable = false)
    private Long btcPrice;

    /**
     * Transaction BTC amount has been forwarded
     */
    @NotNull
    @Column(name = "is_withdrawed", nullable = false)
    private Boolean isWithdrawed;

    /**
     * One Transaction has Many Confidence entries
     */
    @OneToMany(mappedBy = "transaction")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Confidence> confidences = new HashSet<>();

    /**
     * Many Transaction can be done by One User
     */
    @ManyToOne
    @JsonIgnoreProperties(value = "transactions", allowSetters = true)
    private User user;

    @ManyToOne
    @JsonIgnoreProperties(value = "transactions", allowSetters = true)
    private Block block;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getInitiatedAt() {
        return initiatedAt;
    }

    public Transaction initiatedAt(Instant initiatedAt) {
        this.initiatedAt = initiatedAt;
        return this;
    }

    public void setInitiatedAt(Instant initiatedAt) {
        this.initiatedAt = initiatedAt;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public Transaction transactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
        return this;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Boolean isIsMempool() {
        return isMempool;
    }

    public Transaction isMempool(Boolean isMempool) {
        this.isMempool = isMempool;
        return this;
    }

    public void setIsMempool(Boolean isMempool) {
        this.isMempool = isMempool;
    }

    public String getTxHash() {
        return txHash;
    }

    public Transaction txHash(String txHash) {
        this.txHash = txHash;
        return this;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public Transaction fromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
        return this;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public Transaction toAddress(String toAddress) {
        this.toAddress = toAddress;
        return this;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public Long getExpectedAmount() {
        return expectedAmount;
    }

    public Transaction expectedAmount(Long expectedAmount) {
        this.expectedAmount = expectedAmount;
        return this;
    }

    public void setExpectedAmount(Long expectedAmount) {
        this.expectedAmount = expectedAmount;
    }

    public Long getAmount() {
        return amount;
    }

    public Transaction amount(Long amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getServiceFee() {
        return serviceFee;
    }

    public Transaction serviceFee(Long serviceFee) {
        this.serviceFee = serviceFee;
        return this;
    }

    public void setServiceFee(Long serviceFee) {
        this.serviceFee = serviceFee;
    }

    public Long getBtcPrice() {
        return btcPrice;
    }

    public Transaction btcPrice(Long btcPrice) {
        this.btcPrice = btcPrice;
        return this;
    }

    public void setBtcPrice(Long btcPrice) {
        this.btcPrice = btcPrice;
    }

    public Boolean isIsWithdrawed() {
        return isWithdrawed;
    }

    public Transaction isWithdrawed(Boolean isWithdrawed) {
        this.isWithdrawed = isWithdrawed;
        return this;
    }

    public void setIsWithdrawed(Boolean isWithdrawed) {
        this.isWithdrawed = isWithdrawed;
    }

    public Set<Confidence> getConfidences() {
        return confidences;
    }

    public Transaction confidences(Set<Confidence> confidences) {
        this.confidences = confidences;
        return this;
    }

    public Transaction addConfidence(Confidence confidence) {
        this.confidences.add(confidence);
        confidence.setTransaction(this);
        return this;
    }

    public Transaction removeConfidence(Confidence confidence) {
        this.confidences.remove(confidence);
        confidence.setTransaction(null);
        return this;
    }

    public void setConfidences(Set<Confidence> confidences) {
        this.confidences = confidences;
    }

    public User getUser() {
        return user;
    }

    public Transaction user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Block getBlock() {
        return block;
    }

    public Transaction block(Block block) {
        this.block = block;
        return this;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Transaction)) {
            return false;
        }
        return id != null && id.equals(((Transaction) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Transaction{" +
            "id=" + getId() +
            ", initiatedAt='" + getInitiatedAt() + "'" +
            ", transactionType='" + getTransactionType() + "'" +
            ", isMempool='" + isIsMempool() + "'" +
            ", txHash='" + getTxHash() + "'" +
            ", fromAddress='" + getFromAddress() + "'" +
            ", toAddress='" + getToAddress() + "'" +
            ", expectedAmount=" + getExpectedAmount() +
            ", amount=" + getAmount() +
            ", serviceFee=" + getServiceFee() +
            ", btcPrice=" + getBtcPrice() +
            ", isWithdrawed='" + isIsWithdrawed() + "'" +
            "}";
    }
}
