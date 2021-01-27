package com.jkrumm.btcpay.wallet.dto;

import java.time.Instant;

public class WalletDTO {

    private Integer blockHeight;
    private Instant blockMinedAt;
    private Long available;
    private Long availableSpendable;
    private Long estimated;
    private Long estimatedSpendable;

    public WalletDTO(
        Integer blockHeight,
        Instant blockMinedAt,
        Long available,
        Long availableSpendable,
        Long estimated,
        Long estimatedSpendable
    ) {
        this.blockHeight = blockHeight;
        this.blockMinedAt = blockMinedAt;
        this.available = available;
        this.availableSpendable = availableSpendable;
        this.estimated = estimated;
        this.estimatedSpendable = estimatedSpendable;
    }

    public Integer getBlockHeight() {
        return blockHeight;
    }

    public void setBlockHeight(Integer blockHeight) {
        this.blockHeight = blockHeight;
    }

    public Instant getBlockMinedAt() {
        return blockMinedAt;
    }

    public void setBlockMinedAt(Instant blockMinedAt) {
        this.blockMinedAt = blockMinedAt;
    }

    public Long getAvailable() {
        return available;
    }

    public void setAvailable(Long available) {
        this.available = available;
    }

    public Long getAvailableSpendable() {
        return availableSpendable;
    }

    public void setAvailableSpendable(Long availableSpendable) {
        this.availableSpendable = availableSpendable;
    }

    public Long getEstimated() {
        return estimated;
    }

    public void setEstimated(Long estimated) {
        this.estimated = estimated;
    }

    public Long getEstimatedSpendable() {
        return estimatedSpendable;
    }

    public void setEstimatedSpendable(Long estimatedSpendable) {
        this.estimatedSpendable = estimatedSpendable;
    }

    @Override
    public String toString() {
        return (
            "WalletDTO{" +
            "blockHeight=" +
            blockHeight +
            ", blockMinedAt=" +
            blockMinedAt +
            ", available=" +
            available +
            ", availableSpendable=" +
            availableSpendable +
            ", estimated=" +
            estimated +
            ", estimatedSpendable=" +
            estimatedSpendable +
            '}'
        );
    }
}
