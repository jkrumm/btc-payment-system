package com.jkrumm.btcpay.btc.websocket.dto.blockcypher;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "total", "fees", "size", "vsize", "preference", "double_spend", "confirmations", "confidence" })
public class BlockCypherCompactDTO {

    @JsonProperty("total")
    private Integer total;

    @JsonProperty("fees")
    private Integer fees;

    @JsonProperty("size")
    private Integer size;

    @JsonProperty("vsize")
    private Integer vsize;

    @JsonProperty("preference")
    private String preference;

    @JsonProperty("double_spend")
    private Boolean doubleSpend;

    @JsonProperty("confirmations")
    private Integer confirmations;

    @JsonProperty("confidence")
    private Double confidence;

    public BlockCypherCompactDTO(
        Integer total,
        Integer fees,
        Integer size,
        Integer vsize,
        String preference,
        Boolean doubleSpend,
        Integer confirmations,
        Double confidence
    ) {
        this.total = total;
        this.fees = fees;
        this.size = size;
        this.vsize = vsize;
        this.preference = preference;
        this.doubleSpend = doubleSpend;
        this.confirmations = confirmations;
        this.confidence = confidence;
    }

    @JsonProperty("total")
    public Integer getTotal() {
        return total;
    }

    @JsonProperty("total")
    public void setTotal(Integer total) {
        this.total = total;
    }

    @JsonProperty("fees")
    public Integer getFees() {
        return fees;
    }

    @JsonProperty("fees")
    public void setFees(Integer fees) {
        this.fees = fees;
    }

    @JsonProperty("size")
    public Integer getSize() {
        return size;
    }

    @JsonProperty("size")
    public void setSize(Integer size) {
        this.size = size;
    }

    @JsonProperty("vsize")
    public Integer getVsize() {
        return vsize;
    }

    @JsonProperty("vsize")
    public void setVsize(Integer vsize) {
        this.vsize = vsize;
    }

    @JsonProperty("preference")
    public String getPreference() {
        return preference;
    }

    @JsonProperty("preference")
    public void setPreference(String preference) {
        this.preference = preference;
    }

    @JsonProperty("double_spend")
    public Boolean getDoubleSpend() {
        return doubleSpend;
    }

    @JsonProperty("double_spend")
    public void setDoubleSpend(Boolean doubleSpend) {
        this.doubleSpend = doubleSpend;
    }

    @JsonProperty("confirmations")
    public Integer getConfirmations() {
        return confirmations;
    }

    @JsonProperty("confirmations")
    public void setConfirmations(Integer confirmations) {
        this.confirmations = confirmations;
    }

    @JsonProperty("confidence")
    public Double getConfidence() {
        return confidence;
    }

    @JsonProperty("confidence")
    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }

    @Override
    public String toString() {
        return (
            "BlockCypherCompactDTO{" +
            "total=" +
            total +
            ", fees=" +
            fees +
            ", size=" +
            size +
            ", vsize=" +
            vsize +
            ", preference='" +
            preference +
            '\'' +
            ", doubleSpend=" +
            doubleSpend +
            ", confirmations=" +
            confirmations +
            ", confidence=" +
            confidence +
            '}'
        );
    }
}
