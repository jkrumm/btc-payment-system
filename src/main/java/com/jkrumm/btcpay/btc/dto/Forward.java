package com.jkrumm.btcpay.btc.dto;

import java.time.Instant;

public class Forward {

    private Long amount;
    private String to;
    private String txHash;
    private Long fee;
    private Instant initiatedAt;

    public Forward(Long amount, String to, String txHash, Long fee) {
        this.amount = amount;
        this.to = to;
        this.txHash = txHash;
        this.fee = fee;
        this.initiatedAt = Instant.now();
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTxHash() {
        return txHash;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash;
    }

    public Long getFee() {
        return fee;
    }

    public void setFee(Long fee) {
        this.fee = fee;
    }

    public Instant getInitiatedAt() {
        return initiatedAt;
    }

    public void setInitiatedAt(Instant initiatedAt) {
        this.initiatedAt = initiatedAt;
    }

    @Override
    public String toString() {
        return (
            "Forward{" +
            "amount=" +
            amount +
            ", to='" +
            to +
            '\'' +
            ", txHash='" +
            txHash +
            '\'' +
            ", fee=" +
            fee +
            ", initiatedAt=" +
            initiatedAt +
            '}'
        );
    }
}
