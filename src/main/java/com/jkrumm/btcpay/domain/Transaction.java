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
 * Central table to persist transaction details. All holdings will be derived from it with help of BitcoinJ.
 */
@Entity
@Table(name = "transaction")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
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
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type")
    private TransactionType transactionType;

    /**
     * Transaction hash
     */

    @Column(name = "tx_hash", unique = true)
    private String txHash;

    /**
     * Expected BTC amount from the customer
     */
    @Column(name = "expected_amount")
    private Long expectedAmount;

    /**
     * Actual BTC amount of the transaction
     */
    @Column(name = "actual_amount")
    private Long actualAmount;

    /**
     * BTC transaction fee
     */
    @Column(name = "transaction_fee")
    private Long transactionFee;

    /**
     * Service fee
     */
    @Column(name = "service_fee")
    private Long serviceFee;

    /**
     * BTC price at intiation
     */
    @Column(name = "btc_usd")
    private Double btcUsd;

    @Column(name = "address")
    private String address;

    @Column(name = "amount")
    private Double amount;

    /**
     * One Transaction has Many Confidence entries
     */
    @OneToMany(mappedBy = "transaction")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "transaction" }, allowSetters = true)
    private Set<Confidence> confidences = new HashSet<>();

    /**
     * Many Transaction can be done by One User
     */
    @ManyToOne
    private User user;

    /**
     * Many Transaction can be done by One Merchant
     */
    @ManyToOne
    @JsonIgnoreProperties(value = { "fee", "merchantUsers" }, allowSetters = true)
    private Merchant merchant;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Transaction id(Long id) {
        this.id = id;
        return this;
    }

    public Instant getInitiatedAt() {
        return this.initiatedAt;
    }

    public Transaction initiatedAt(Instant initiatedAt) {
        this.initiatedAt = initiatedAt;
        return this;
    }

    public void setInitiatedAt(Instant initiatedAt) {
        this.initiatedAt = initiatedAt;
    }

    public TransactionType getTransactionType() {
        return this.transactionType;
    }

    public Transaction transactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
        return this;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public String getTxHash() {
        return this.txHash;
    }

    public Transaction txHash(String txHash) {
        this.txHash = txHash;
        return this;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash;
    }

    public Long getExpectedAmount() {
        return this.expectedAmount;
    }

    public Transaction expectedAmount(Long expectedAmount) {
        this.expectedAmount = expectedAmount;
        return this;
    }

    public void setExpectedAmount(Long expectedAmount) {
        this.expectedAmount = expectedAmount;
    }

    public Long getActualAmount() {
        return this.actualAmount;
    }

    public Transaction actualAmount(Long actualAmount) {
        this.actualAmount = actualAmount;
        return this;
    }

    public void setActualAmount(Long actualAmount) {
        this.actualAmount = actualAmount;
    }

    public Long getTransactionFee() {
        return this.transactionFee;
    }

    public Transaction transactionFee(Long transactionFee) {
        this.transactionFee = transactionFee;
        return this;
    }

    public void setTransactionFee(Long transactionFee) {
        this.transactionFee = transactionFee;
    }

    public Long getServiceFee() {
        return this.serviceFee;
    }

    public Transaction serviceFee(Long serviceFee) {
        this.serviceFee = serviceFee;
        return this;
    }

    public void setServiceFee(Long serviceFee) {
        this.serviceFee = serviceFee;
    }

    public Double getBtcUsd() {
        return this.btcUsd;
    }

    public Transaction btcUsd(Double btcUsd) {
        this.btcUsd = btcUsd;
        return this;
    }

    public void setBtcUsd(Double btcUsd) {
        this.btcUsd = btcUsd;
    }

    public String getAddress() {
        return this.address;
    }

    public Transaction address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getAmount() {
        return this.amount;
    }

    public Transaction amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Set<Confidence> getConfidences() {
        return this.confidences;
    }

    public Transaction confidences(Set<Confidence> confidences) {
        this.setConfidences(confidences);
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
        if (this.confidences != null) {
            this.confidences.forEach(i -> i.setTransaction(null));
        }
        if (confidences != null) {
            confidences.forEach(i -> i.setTransaction(this));
        }
        this.confidences = confidences;
    }

    public User getUser() {
        return this.user;
    }

    public Transaction user(User user) {
        this.setUser(user);
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Merchant getMerchant() {
        return this.merchant;
    }

    public Transaction merchant(Merchant merchant) {
        this.setMerchant(merchant);
        return this;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
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
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Transaction{" +
            "id=" + getId() +
            ", initiatedAt='" + getInitiatedAt() + "'" +
            ", transactionType='" + getTransactionType() + "'" +
            ", txHash='" + getTxHash() + "'" +
            ", expectedAmount=" + getExpectedAmount() +
            ", actualAmount=" + getActualAmount() +
            ", transactionFee=" + getTransactionFee() +
            ", serviceFee=" + getServiceFee() +
            ", btcUsd=" + getBtcUsd() +
            ", address='" + getAddress() + "'" +
            ", amount=" + getAmount() +
            "}";
    }
}
