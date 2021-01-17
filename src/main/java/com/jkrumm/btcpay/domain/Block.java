package com.jkrumm.btcpay.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Wallet status after each added Block
 */
@Entity
@Table(name = "block")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Block implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Timestamp new block was added
     */
    @NotNull
    @Column(name = "mined_at", nullable = false)
    private Instant minedAt;

    /**
     * Block ID
     */

    @Column(name = "block", unique = true)
    private Long block;

    @NotNull
    @Column(name = "available", nullable = false)
    private Long available;

    @NotNull
    @Column(name = "estimated", nullable = false)
    private Long estimated;

    @NotNull
    @Column(name = "available_spendable", nullable = false)
    private Long availableSpendable;

    @NotNull
    @Column(name = "estimated_spendable", nullable = false)
    private Long estimatedSpendable;

    /**
     * One Block holds Many Transactions
     */
    @OneToMany(mappedBy = "block")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Transaction> transactions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getMinedAt() {
        return minedAt;
    }

    public Block minedAt(Instant minedAt) {
        this.minedAt = minedAt;
        return this;
    }

    public void setMinedAt(Instant minedAt) {
        this.minedAt = minedAt;
    }

    public Long getBlock() {
        return block;
    }

    public Block block(Long block) {
        this.block = block;
        return this;
    }

    public void setBlock(Long block) {
        this.block = block;
    }

    public Long getAvailable() {
        return available;
    }

    public Block available(Long available) {
        this.available = available;
        return this;
    }

    public void setAvailable(Long available) {
        this.available = available;
    }

    public Long getEstimated() {
        return estimated;
    }

    public Block estimated(Long estimated) {
        this.estimated = estimated;
        return this;
    }

    public void setEstimated(Long estimated) {
        this.estimated = estimated;
    }

    public Long getAvailableSpendable() {
        return availableSpendable;
    }

    public Block availableSpendable(Long availableSpendable) {
        this.availableSpendable = availableSpendable;
        return this;
    }

    public void setAvailableSpendable(Long availableSpendable) {
        this.availableSpendable = availableSpendable;
    }

    public Long getEstimatedSpendable() {
        return estimatedSpendable;
    }

    public Block estimatedSpendable(Long estimatedSpendable) {
        this.estimatedSpendable = estimatedSpendable;
        return this;
    }

    public void setEstimatedSpendable(Long estimatedSpendable) {
        this.estimatedSpendable = estimatedSpendable;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public Block transactions(Set<Transaction> transactions) {
        this.transactions = transactions;
        return this;
    }

    public Block addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
        transaction.setBlock(this);
        return this;
    }

    public Block removeTransaction(Transaction transaction) {
        this.transactions.remove(transaction);
        transaction.setBlock(null);
        return this;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Block)) {
            return false;
        }
        return id != null && id.equals(((Block) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Block{" +
            "id=" + getId() +
            ", minedAt='" + getMinedAt() + "'" +
            ", block=" + getBlock() +
            ", available=" + getAvailable() +
            ", estimated=" + getEstimated() +
            ", availableSpendable=" + getAvailableSpendable() +
            ", estimatedSpendable=" + getEstimatedSpendable() +
            "}";
    }
}
