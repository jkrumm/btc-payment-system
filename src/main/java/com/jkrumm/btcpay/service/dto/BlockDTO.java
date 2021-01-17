package com.jkrumm.btcpay.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.Instant;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.jkrumm.btcpay.domain.Block} entity.
 */
@ApiModel(description = "Wallet status after each added Block")
public class BlockDTO implements Serializable {
    private Long id;

    /**
     * Timestamp new block was added
     */
    @NotNull
    @ApiModelProperty(value = "Timestamp new block was added", required = true)
    private Instant minedAt;

    /**
     * Block ID
     */

    @ApiModelProperty(value = "Block ID")
    private Long block;

    @NotNull
    private Long available;

    @NotNull
    private Long estimated;

    @NotNull
    private Long availableSpendable;

    @NotNull
    private Long estimatedSpendable;

    /**
     * One Block holds Many Transactions
     */
    @ApiModelProperty(value = "One Block holds Many Transactions")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getMinedAt() {
        return minedAt;
    }

    public void setMinedAt(Instant minedAt) {
        this.minedAt = minedAt;
    }

    public Long getBlock() {
        return block;
    }

    public void setBlock(Long block) {
        this.block = block;
    }

    public Long getAvailable() {
        return available;
    }

    public void setAvailable(Long available) {
        this.available = available;
    }

    public Long getEstimated() {
        return estimated;
    }

    public void setEstimated(Long estimated) {
        this.estimated = estimated;
    }

    public Long getAvailableSpendable() {
        return availableSpendable;
    }

    public void setAvailableSpendable(Long availableSpendable) {
        this.availableSpendable = availableSpendable;
    }

    public Long getEstimatedSpendable() {
        return estimatedSpendable;
    }

    public void setEstimatedSpendable(Long estimatedSpendable) {
        this.estimatedSpendable = estimatedSpendable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BlockDTO)) {
            return false;
        }

        return id != null && id.equals(((BlockDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BlockDTO{" +
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
