package com.jkrumm.btcpay.btc.dto;

import com.jkrumm.btcpay.domain.enumeration.ConfidenceType;
import com.jkrumm.btcpay.domain.enumeration.TransactionType;
import java.time.Instant;
import java.util.List;

public class TransactionHistory {

    private Long id;
    private String user;
    private String address;
    private Instant initiatedAt;
    private String transactionType;
    private String txHash;
    private Long expectedAmount;
    private Long actualAmount;
    private Long transactionFee;
    private Long serviceFee;
    private Double btcUsd;
    private Double amount;
    List<TransactionHistoryConfidence> confidences;
    private String timeAgo;
    private ConfidenceType confidenceType;
    private Integer confirmations;

    public TransactionHistory(
        Long id,
        String user,
        String address,
        Instant initiatedAt,
        TransactionType transactionType,
        String txHash,
        Long expectedAmount,
        Long actualAmount,
        Long transactionFee,
        Long serviceFee,
        Double btcUsd,
        Double amount,
        List<TransactionHistoryConfidence> confidences,
        String timeAgo,
        ConfidenceType confidenceType,
        Integer confirmations
    ) {
        this.id = id;
        this.user = user;
        this.address = address;
        this.initiatedAt = initiatedAt;
        switch (transactionType) {
            case INCOMING_CUSTOMER:
                this.transactionType = "INCOMING";
                break;
            case FORWARD_HOLDINGS:
                this.transactionType = "FORWARD_HOLDINGS";
                break;
            case FORWARD_MERCHANT:
                this.transactionType = "FORWARD";
                break;
            case INCOMING_UNKNOWN:
                this.transactionType = "INCOMING_UNKNOWN";
                break;
        }
        this.txHash = txHash;
        this.expectedAmount = expectedAmount;
        this.actualAmount = actualAmount;
        this.transactionFee = transactionFee;
        this.serviceFee = serviceFee;
        this.btcUsd = btcUsd;
        this.amount = amount;
        this.confidences = confidences;
        this.timeAgo = timeAgo;
        this.confidenceType = confidenceType;
        this.confirmations = confirmations;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Instant getInitiatedAt() {
        return initiatedAt;
    }

    public void setInitiatedAt(Instant initiatedAt) {
        this.initiatedAt = initiatedAt;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTxHash() {
        return txHash;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash;
    }

    public Long getExpectedAmount() {
        return expectedAmount;
    }

    public void setExpectedAmount(Long expectedAmount) {
        this.expectedAmount = expectedAmount;
    }

    public Long getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(Long actualAmount) {
        this.actualAmount = actualAmount;
    }

    public Long getTransactionFee() {
        return transactionFee;
    }

    public void setTransactionFee(Long transactionFee) {
        this.transactionFee = transactionFee;
    }

    public Long getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(Long serviceFee) {
        this.serviceFee = serviceFee;
    }

    public Double getBtcUsd() {
        return btcUsd;
    }

    public void setBtcUsd(Double btcUsd) {
        this.btcUsd = btcUsd;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public List<TransactionHistoryConfidence> getConfidences() {
        return confidences;
    }

    public void setConfidences(List<TransactionHistoryConfidence> confidences) {
        this.confidences = confidences;
    }

    public String getTimeAgo() {
        return timeAgo;
    }

    public void setTimeAgo(String timeAgo) {
        this.timeAgo = timeAgo;
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
            "TransactionHistory{" +
            "id=" +
            id +
            ", user='" +
            user +
            ", address='" +
            address +
            '\'' +
            ", initiatedAt=" +
            initiatedAt +
            ", transactionType=" +
            transactionType +
            ", txHash='" +
            txHash +
            '\'' +
            ", expectedAmount=" +
            expectedAmount +
            ", actualAmount=" +
            actualAmount +
            ", transactionFee=" +
            transactionFee +
            ", serviceFee=" +
            serviceFee +
            ", btcUsd=" +
            btcUsd +
            ", amount=" +
            amount +
            ", confidences=" +
            confidences +
            ", timeAgo='" +
            timeAgo +
            '\'' +
            ", confidenceType=" +
            confidenceType +
            ", confirmations=" +
            confirmations +
            '}'
        );
    }
}
