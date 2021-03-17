package com.jkrumm.btcpay.service.dto;

import com.jkrumm.btcpay.domain.enumeration.TransactionType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.jkrumm.btcpay.domain.Transaction} entity.
 */
@ApiModel(description = "Central table to persist transaction details. All holdings will be derived from it with help of BitcoinJ.")
public class TransactionDTO implements Serializable {

    private Long id;

    /**
     * Transaction initiated at
     */
    @NotNull
    @ApiModelProperty(value = "Transaction initiated at", required = true)
    private Instant initiatedAt;

    /**
     * Transaction enum type
     */
    @ApiModelProperty(value = "Transaction enum type")
    private TransactionType transactionType;

    /**
     * Transaction hash
     */

    @ApiModelProperty(value = "Transaction hash")
    private String txHash;

    /**
     * Expected BTC amount from the customer
     */
    @ApiModelProperty(value = "Expected BTC amount from the customer")
    private Long expectedAmount;

    /**
     * Actual BTC amount of the transaction
     */
    @ApiModelProperty(value = "Actual BTC amount of the transaction")
    private Long actualAmount;

    /**
     * BTC transaction fee
     */
    @ApiModelProperty(value = "BTC transaction fee")
    private Long transactionFee;

    /**
     * Service fee
     */
    @ApiModelProperty(value = "Service fee")
    private Long serviceFee;

    /**
     * BTC/Euro price at intiation
     */
    @ApiModelProperty(value = "BTC/Euro price at intiation")
    private Double btcEuro;

    /**
     * Transaction address
     */
    @ApiModelProperty(value = "Transaction address")
    private String address;

    /**
     * Euro price
     */
    @ApiModelProperty(value = "Euro price")
    private Double amount;

    private UserDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getInitiatedAt() {
        return initiatedAt;
    }

    public void setInitiatedAt(Instant initiatedAt) {
        this.initiatedAt = initiatedAt;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
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

    public Double getBtcEuro() {
        return btcEuro;
    }

    public void setBtcEuro(Double btcEuro) {
        this.btcEuro = btcEuro;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransactionDTO)) {
            return false;
        }

        TransactionDTO transactionDTO = (TransactionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, transactionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransactionDTO{" +
            "id=" + getId() +
            ", initiatedAt='" + getInitiatedAt() + "'" +
            ", transactionType='" + getTransactionType() + "'" +
            ", txHash='" + getTxHash() + "'" +
            ", expectedAmount=" + getExpectedAmount() +
            ", actualAmount=" + getActualAmount() +
            ", transactionFee=" + getTransactionFee() +
            ", serviceFee=" + getServiceFee() +
            ", btcEuro=" + getBtcEuro() +
            ", address='" + getAddress() + "'" +
            ", amount=" + getAmount() +
            ", user=" + getUser() +
            "}";
    }
}
