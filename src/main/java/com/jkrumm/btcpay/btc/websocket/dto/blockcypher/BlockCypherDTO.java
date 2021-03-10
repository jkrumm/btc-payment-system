package com.jkrumm.btcpay.btc.websocket.dto.blockcypher;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
    {
        "block_hash",
        "block_height",
        "block_index",
        "hash",
        "addresses",
        "total",
        "fees",
        "size",
        "vsize",
        "preference",
        "confirmed",
        "received",
        "ver",
        "double_spend",
        "vin_sz",
        "vout_sz",
        "confirmations",
        "confidence",
        "inputs",
        "outputs",
    }
)
public class BlockCypherDTO {

    @JsonProperty("block_hash")
    private String blockHash;

    @JsonProperty("block_height")
    private Integer blockHeight;

    @JsonProperty("block_index")
    private Integer blockIndex;

    @JsonProperty("hash")
    private String hash;

    @JsonProperty("addresses")
    private List<String> addresses = null;

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

    @JsonProperty("confirmed")
    private String confirmed;

    @JsonProperty("received")
    private String received;

    @JsonProperty("ver")
    private Integer ver;

    @JsonProperty("double_spend")
    private Boolean doubleSpend;

    @JsonProperty("vin_sz")
    private Integer vinSz;

    @JsonProperty("vout_sz")
    private Integer voutSz;

    @JsonProperty("confirmations")
    private Integer confirmations;

    @JsonProperty("confidence")
    private Double confidence;

    @JsonProperty("inputs")
    private List<Input> inputs = null;

    @JsonProperty("outputs")
    private List<Output> outputs = null;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("block_hash")
    public String getBlockHash() {
        return blockHash;
    }

    @JsonProperty("block_hash")
    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }

    @JsonProperty("block_height")
    public Integer getBlockHeight() {
        return blockHeight;
    }

    @JsonProperty("block_height")
    public void setBlockHeight(Integer blockHeight) {
        this.blockHeight = blockHeight;
    }

    @JsonProperty("block_index")
    public Integer getBlockIndex() {
        return blockIndex;
    }

    @JsonProperty("block_index")
    public void setBlockIndex(Integer blockIndex) {
        this.blockIndex = blockIndex;
    }

    @JsonProperty("hash")
    public String getHash() {
        return hash;
    }

    @JsonProperty("hash")
    public void setHash(String hash) {
        this.hash = hash;
    }

    @JsonProperty("addresses")
    public List<String> getAddresses() {
        return addresses;
    }

    @JsonProperty("addresses")
    public void setAddresses(List<String> addresses) {
        this.addresses = addresses;
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

    @JsonProperty("confirmed")
    public String getConfirmed() {
        return confirmed;
    }

    @JsonProperty("confirmed")
    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }

    @JsonProperty("received")
    public String getReceived() {
        return received;
    }

    @JsonProperty("received")
    public void setReceived(String received) {
        this.received = received;
    }

    @JsonProperty("ver")
    public Integer getVer() {
        return ver;
    }

    @JsonProperty("ver")
    public void setVer(Integer ver) {
        this.ver = ver;
    }

    @JsonProperty("double_spend")
    public Boolean getDoubleSpend() {
        return doubleSpend;
    }

    @JsonProperty("double_spend")
    public void setDoubleSpend(Boolean doubleSpend) {
        this.doubleSpend = doubleSpend;
    }

    @JsonProperty("vin_sz")
    public Integer getVinSz() {
        return vinSz;
    }

    @JsonProperty("vin_sz")
    public void setVinSz(Integer vinSz) {
        this.vinSz = vinSz;
    }

    @JsonProperty("vout_sz")
    public Integer getVoutSz() {
        return voutSz;
    }

    @JsonProperty("vout_sz")
    public void setVoutSz(Integer voutSz) {
        this.voutSz = voutSz;
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

    @JsonProperty("inputs")
    public List<Input> getInputs() {
        return inputs;
    }

    @JsonProperty("inputs")
    public void setInputs(List<Input> inputs) {
        this.inputs = inputs;
    }

    @JsonProperty("outputs")
    public List<Output> getOutputs() {
        return outputs;
    }

    @JsonProperty("outputs")
    public void setOutputs(List<Output> outputs) {
        this.outputs = outputs;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return (
            "BlockCypherDTO{" +
            "blockHash='" +
            blockHash +
            '\'' +
            ", blockHeight=" +
            blockHeight +
            ", blockIndex=" +
            blockIndex +
            ", hash='" +
            hash +
            '\'' +
            ", addresses=" +
            addresses +
            ", total=" +
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
            ", confirmed='" +
            confirmed +
            '\'' +
            ", received='" +
            received +
            '\'' +
            ", ver=" +
            ver +
            ", doubleSpend=" +
            doubleSpend +
            ", vinSz=" +
            vinSz +
            ", voutSz=" +
            voutSz +
            ", confirmations=" +
            confirmations +
            ", confidence=" +
            confidence +
            ", inputs=" +
            inputs +
            ", outputs=" +
            outputs +
            ", additionalProperties=" +
            additionalProperties +
            '}'
        );
    }
}
