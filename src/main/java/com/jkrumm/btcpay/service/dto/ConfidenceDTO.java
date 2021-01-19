package com.jkrumm.btcpay.service.dto;

import com.jkrumm.btcpay.domain.enumeration.ConfidenceType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.jkrumm.btcpay.domain.Confidence} entity.
 */
@ApiModel(description = "Store history of a Trasaction confidence")
public class ConfidenceDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant changeAt;

    /**
     * Current state of a transaction
     */
    @NotNull
    @ApiModelProperty(value = "Current state of a transaction", required = true)
    private ConfidenceType confidenceType;

    /**
     * Amount of confirmations through new blocks
     */
    @NotNull
    @Min(value = 0)
    @Max(value = 6)
    @ApiModelProperty(value = "Amount of confirmations through new blocks", required = true)
    private Integer confirmations;

    private TransactionDTO transaction;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getChangeAt() {
        return changeAt;
    }

    public void setChangeAt(Instant changeAt) {
        this.changeAt = changeAt;
    }

    public ConfidenceType getConfidenceType() {
        return confidenceType;
    }

    public void setConfidenceType(ConfidenceType confidenceType) {
        this.confidenceType = confidenceType;
    }

    public Integer getConfirmations() {
        return confirmations;
    }

    public void setConfirmations(Integer confirmations) {
        this.confirmations = confirmations;
    }

    public TransactionDTO getTransaction() {
        return transaction;
    }

    public void setTransaction(TransactionDTO transaction) {
        this.transaction = transaction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConfidenceDTO)) {
            return false;
        }

        ConfidenceDTO confidenceDTO = (ConfidenceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, confidenceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConfidenceDTO{" +
            "id=" + getId() +
            ", changeAt='" + getChangeAt() + "'" +
            ", confidenceType='" + getConfidenceType() + "'" +
            ", confirmations=" + getConfirmations() +
            ", transaction=" + getTransaction() +
            "}";
    }
}
