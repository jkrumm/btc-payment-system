package com.jkrumm.btcpay.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jkrumm.btcpay.domain.enumeration.ConfidenceType;
import java.io.Serializable;
import javax.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Current state of a transaction
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "confidence_type")
    private ConfidenceType confidenceType;

    /**
     * Amount of confirmations through new blocks
     */
    @Column(name = "confirmations")
    private Integer confirmations;

    @ManyToOne
    @JsonIgnoreProperties(value = "confidences", allowSetters = true)
    private Transaction transaction;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ConfidenceType getConfidenceType() {
        return confidenceType;
    }

    public Confidence confidenceType(ConfidenceType confidenceType) {
        this.confidenceType = confidenceType;
        return this;
    }

    public void setConfidenceType(ConfidenceType confidenceType) {
        this.confidenceType = confidenceType;
    }

    public Integer getConfirmations() {
        return confirmations;
    }

    public Confidence confirmations(Integer confirmations) {
        this.confirmations = confirmations;
        return this;
    }

    public void setConfirmations(Integer confirmations) {
        this.confirmations = confirmations;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public Confidence transaction(Transaction transaction) {
        this.transaction = transaction;
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
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Confidence{" +
            "id=" + getId() +
            ", confidenceType='" + getConfidenceType() + "'" +
            ", confirmations=" + getConfirmations() +
            "}";
    }
}
