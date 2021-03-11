package com.jkrumm.btcpay.btc.dto;

import com.jkrumm.btcpay.domain.enumeration.ConfidenceType;
import java.time.Instant;

public class TransactionHistoryConfidence {

    private Long id;
    private Instant changeAt;
    private ConfidenceType confidenceType;
    private Integer confirmations;

    public TransactionHistoryConfidence(Long id, Instant changeAt, ConfidenceType confidenceType, Integer confirmations) {
        this.id = id;
        this.changeAt = changeAt;
        this.confidenceType = confidenceType;
        this.confirmations = confirmations;
    }

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

    @Override
    public String toString() {
        return (
            "TransactionHistoryConfidence{" +
            "id='" +
            id +
            '\'' +
            ", changeAt=" +
            changeAt +
            ", confidenceType=" +
            confidenceType +
            ", confirmations=" +
            confirmations +
            '}'
        );
    }
}
