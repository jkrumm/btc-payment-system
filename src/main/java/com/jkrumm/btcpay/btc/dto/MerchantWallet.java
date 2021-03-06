package com.jkrumm.btcpay.btc.dto;

public class MerchantWallet {

    private Long estimated;
    private Double estimatedUsd;
    private Long spendable;
    private Double spendableUsd;
    private Long serviceFee;
    private Double serviceFeeUsd;
    private Long forward;
    private Double forwardUsd;
    private Long total;
    private Double totalUsd;

    public MerchantWallet() {
        this.estimated = 0L;
        this.estimatedUsd = 0.0;
        this.spendable = 0L;
        this.spendableUsd = 0.0;
        this.serviceFee = 0L;
        this.serviceFeeUsd = 0.0;
        this.forward = 0L;
        this.forwardUsd = 0.0;
        this.total = 0L;
        this.totalUsd = 0.0;
    }

    public Long getEstimated() {
        return estimated;
    }

    public void setEstimated(Long estimated) {
        this.estimated = estimated;
    }

    public Double getEstimatedUsd() {
        return estimatedUsd;
    }

    public void setEstimatedUsd(Double estimatedUsd) {
        this.estimatedUsd = estimatedUsd;
    }

    public Long getSpendable() {
        return spendable;
    }

    public void setSpendable(Long spendable) {
        this.spendable = spendable;
    }

    public Double getSpendableUsd() {
        return spendableUsd;
    }

    public void setSpendableUsd(Double spendableUsd) {
        this.spendableUsd = spendableUsd;
    }

    public Long getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(Long serviceFee) {
        this.serviceFee = serviceFee;
    }

    public Double getServiceFeeUsd() {
        return serviceFeeUsd;
    }

    public void setServiceFeeUsd(Double serviceFeeUsd) {
        this.serviceFeeUsd = serviceFeeUsd;
    }

    public Long getForward() {
        return forward;
    }

    public void setForward(Long forward) {
        this.forward = forward;
    }

    public Double getForwardUsd() {
        return forwardUsd;
    }

    public void setForwardUsd(Double forwardUsd) {
        this.forwardUsd = forwardUsd;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Double getTotalUsd() {
        return totalUsd;
    }

    public void setTotalUsd(Double totalUsd) {
        this.totalUsd = totalUsd;
    }

    @Override
    public String toString() {
        return (
            "MerchantWallet{" +
            "estimated=" +
            estimated +
            ", estimatedUsd=" +
            estimatedUsd +
            ", spendable=" +
            spendable +
            ", spendableUsd=" +
            spendableUsd +
            ", serviceFee=" +
            serviceFee +
            ", serviceFeeUsd=" +
            serviceFeeUsd +
            ", forward=" +
            forward +
            ", forwardUsd=" +
            forwardUsd +
            ", total=" +
            total +
            ", totalUsd=" +
            totalUsd +
            '}'
        );
    }
}
