package com.jkrumm.btcpay.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jkrumm.btcpay.domain.enumeration.ConfidenceType;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Store history of a Trasaction confidence
 */
@Entity
@Table(name = "confidence")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Confidence implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "change_at", nullable = false)
    private Instant changeAt;

    /**
     * Current state of a transaction
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "confidence_type", nullable = false)
    private ConfidenceType confidenceType;

    /**
     * Amount of confirmations through new blocks
     */
    @NotNull
    @Min(value = 0)
    @Max(value = 6)
    @Column(name = "confirmations", nullable = false)
    private Integer confirmations;

    @ManyToOne
    @JsonIgnoreProperties(value = { "confidences", "user" }, allowSetters = true)
    private Transaction transaction;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Confidence id(Long id) {
        this.id = id;
        return this;
    }

    public Instant getChangeAt() {
        return this.changeAt;
    }

    public Confidence changeAt(Instant changeAt) {
        this.changeAt = changeAt;
        return this;
    }

    public void setChangeAt(Instant changeAt) {
        this.changeAt = changeAt;
    }

    public ConfidenceType getConfidenceType() {
        return this.confidenceType;
    }

    public Confidence confidenceType(ConfidenceType confidenceType) {
        this.confidenceType = confidenceType;
        return this;
    }

    public void setConfidenceType(ConfidenceType confidenceType) {
        this.confidenceType = confidenceType;
    }

    public Integer getConfirmations() {
        return this.confirmations;
    }

    public Confidence confirmations(Integer confirmations) {
        this.confirmations = confirmations;
        return this;
    }

    public void setConfirmations(Integer confirmations) {
        this.confirmations = confirmations;
    }

    public Transaction getTransaction() {
        return this.transaction;
    }

    public Confidence transaction(Transaction transaction) {
        this.setTransaction(transaction);
        return this;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Confidence)) {
            return false;
        }
        return id != null && id.equals(((Confidence) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Confidence{" +
            "id=" + getId() +
            ", changeAt='" + getChangeAt() + "'" +
            ", confidenceType='" + getConfidenceType() + "'" +
            ", confirmations=" + getConfirmations() +
            "}";
    }
}
