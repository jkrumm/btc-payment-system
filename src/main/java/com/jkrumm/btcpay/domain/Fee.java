package com.jkrumm.btcpay.domain;

import com.jkrumm.btcpay.domain.enumeration.FeeType;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Associated Merchant Fee
 */
@Entity
@Table(name = "fee")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Fee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * Fees can be ZERO, LOW, HIGH
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "fee_type", nullable = false)
    private FeeType feeType;

    /**
     * Percentage for a small amount transaction
     */
    @NotNull
    @Column(name = "percent", precision = 21, scale = 2, nullable = false)
    private BigDecimal percent;

    /**
     * Percentage for a high amount transaction
     */
    @NotNull
    @Column(name = "percent_secure", precision = 21, scale = 2, nullable = false)
    private BigDecimal percentSecure;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Fee id(Long id) {
        this.id = id;
        return this;
    }

    public FeeType getFeeType() {
        return this.feeType;
    }

    public Fee feeType(FeeType feeType) {
        this.feeType = feeType;
        return this;
    }

    public void setFeeType(FeeType feeType) {
        this.feeType = feeType;
    }

    public BigDecimal getPercent() {
        return this.percent;
    }

    public Fee percent(BigDecimal percent) {
        this.percent = percent;
        return this;
    }

    public void setPercent(BigDecimal percent) {
        this.percent = percent;
    }

    public BigDecimal getPercentSecure() {
        return this.percentSecure;
    }

    public Fee percentSecure(BigDecimal percentSecure) {
        this.percentSecure = percentSecure;
        return this;
    }

    public void setPercentSecure(BigDecimal percentSecure) {
        this.percentSecure = percentSecure;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Fee)) {
            return false;
        }
        return id != null && id.equals(((Fee) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Fee{" +
            "id=" + getId() +
            ", feeType='" + getFeeType() + "'" +
            ", percent=" + getPercent() +
            ", percentSecure=" + getPercentSecure() +
            "}";
    }
}
