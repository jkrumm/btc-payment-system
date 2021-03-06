package com.jkrumm.btcpay.btc.dto;

import com.jkrumm.btcpay.domain.enumeration.FeeType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.validation.constraints.NotNull;

/**
 * A DTO for the {@link com.jkrumm.btcpay.domain.Fee} entity.
 */
@ApiModel(description = "StoredBlock DTO")
public class StoredBlockDTO implements Serializable {

    private Long id;

    /**
     * Fees can be ZERO, LOW, HIGH
     */
    @NotNull
    @ApiModelProperty(value = "Fees can be ZERO, LOW, HIGH", required = true)
    private FeeType feeType;

    /**
     * Percentage for a small amount transaction
     */
    @NotNull
    @ApiModelProperty(value = "Percentage for a small amount transaction", required = true)
    private BigDecimal percent;

    /**
     * Percentage for a high amount transaction
     */
    @NotNull
    @ApiModelProperty(value = "Percentage for a high amount transaction", required = true)
    private BigDecimal percentSecure;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FeeType getFeeType() {
        return feeType;
    }

    public void setFeeType(FeeType feeType) {
        this.feeType = feeType;
    }

    public BigDecimal getPercent() {
        return percent;
    }

    public void setPercent(BigDecimal percent) {
        this.percent = percent;
    }

    public BigDecimal getPercentSecure() {
        return percentSecure;
    }

    public void setPercentSecure(BigDecimal percentSecure) {
        this.percentSecure = percentSecure;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StoredBlockDTO)) {
            return false;
        }

        StoredBlockDTO feeDTO = (StoredBlockDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, feeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FeeDTO{" +
            "id=" + getId() +
            ", feeType='" + getFeeType() + "'" +
            ", percent=" + getPercent() +
            ", percentSecure=" + getPercentSecure() +
            "}";
    }
}
