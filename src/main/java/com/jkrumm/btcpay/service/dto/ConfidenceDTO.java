package com.jkrumm.btcpay.service.dto;

import com.jkrumm.btcpay.domain.enumeration.ConfidenceType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/**
 * A DTO for the {@link com.jkrumm.btcpay.domain.Confidence} entity.
 */
@ApiModel(description = "Store history of a Trasaction confidence")
public class ConfidenceDTO implements Serializable {
    private Long id;

    /**
     * Current state of a transaction
     */
    @ApiModelProperty(value = "Current state of a transaction")
    private ConfidenceType confidenceType;

    /**
     * Amount of confirmations through new blocks
     */
    @ApiModelProperty(value = "Amount of confirmations through new blocks")
    private Integer confirmations;

    private Long transactionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConfidenceDTO)) {
            return false;
        }

        return id != null && id.equals(((ConfidenceDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConfidenceDTO{" +
            "id=" + getId() +
            ", confidenceType='" + getConfidenceType() + "'" +
            ", confirmations=" + getConfirmations() +
            ", transactionId=" + getTransactionId() +
            "}";
    }
}
