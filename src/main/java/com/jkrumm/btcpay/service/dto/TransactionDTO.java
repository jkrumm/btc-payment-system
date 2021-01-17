package com.jkrumm.btcpay.service.dto;

import com.jkrumm.btcpay.domain.enumeration.TransactionType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.Instant;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.jkrumm.btcpay.domain.Transaction} entity.
 */
@ApiModel(description = "Central table to persist transaction details. All holdings will be derived from it.")
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
    @NotNull
    @ApiModelProperty(value = "Transaction enum type", required = true)
    private TransactionType transactionType;

    /**
     * Is Transaction still in the Mempool
     */
    @NotNull
    @ApiModelProperty(value = "Is Transaction still in the Mempool", required = true)
    private Boolean isMempool;

    /**
     * Transaction hash
     */

    @ApiModelProperty(value = "Transaction hash")
    private String txHash;

    /**
     * Transaction send from address
     */
    @ApiModelProperty(value = "Transaction send from address")
    private String fromAddress;

    /**
     * Transaction send to address
     */
    @NotNull
    @ApiModelProperty(value = "Transaction send to address", required = true)
    private String toAddress;

    /**
     * Expected BTC amount from the customer
     */
    @NotNull
    @ApiModelProperty(value = "Expected BTC amount from the customer", required = true)
    private Long expectedAmount;

    /**
     * Actual BTC amount of the transaction
     */
    @ApiModelProperty(value = "Actual BTC amount of the transaction")
    private Long amount;

    /**
     * Service fee
     */
    @NotNull
    @ApiModelProperty(value = "Service fee", required = true)
    private Long serviceFee;

    /**
     * BTC price at intiation
     */
    @NotNull
    @ApiModelProperty(value = "BTC price at intiation", required = true)
    private Long btcPrice;

    /**
     * Transaction BTC amount has been forwarded
     */
    @NotNull
    @ApiModelProperty(value = "Transaction BTC amount has been forwarded", required = true)
    private Boolean isWithdrawed;

    /**
     * One Transaction has Many Confidence entries
     */
    @ApiModelProperty(value = "One Transaction has Many Confidence entries")
    private Long userId;

    private Long blockId;

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

    public Boolean isIsMempool() {
        return isMempool;
    }

    public void setIsMempool(Boolean isMempool) {
        this.isMempool = isMempool;
    }

    public String getTxHash() {
        return txHash;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public Long getExpectedAmount() {
        return expectedAmount;
    }

    public void setExpectedAmount(Long expectedAmount) {
        this.expectedAmount = expectedAmount;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(Long serviceFee) {
        this.serviceFee = serviceFee;
    }

    public Long getBtcPrice() {
        return btcPrice;
    }

    public void setBtcPrice(Long btcPrice) {
        this.btcPrice = btcPrice;
    }

    public Boolean isIsWithdrawed() {
        return isWithdrawed;
    }

    public void setIsWithdrawed(Boolean isWithdrawed) {
        this.isWithdrawed = isWithdrawed;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBlockId() {
        return blockId;
    }

    public void setBlockId(Long blockId) {
        this.blockId = blockId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransactionDTO)) {
            return false;
        }

        return id != null && id.equals(((TransactionDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransactionDTO{" +
            "id=" + getId() +
            ", initiatedAt='" + getInitiatedAt() + "'" +
            ", transactionType='" + getTransactionType() + "'" +
            ", isMempool='" + isIsMempool() + "'" +
            ", txHash='" + getTxHash() + "'" +
            ", fromAddress='" + getFromAddress() + "'" +
            ", toAddress='" + getToAddress() + "'" +
            ", expectedAmount=" + getExpectedAmount() +
            ", amount=" + getAmount() +
            ", serviceFee=" + getServiceFee() +
            ", btcPrice=" + getBtcPrice() +
            ", isWithdrawed='" + isIsWithdrawed() + "'" +
            ", userId=" + getUserId() +
            ", blockId=" + getBlockId() +
            "}";
    }
}
