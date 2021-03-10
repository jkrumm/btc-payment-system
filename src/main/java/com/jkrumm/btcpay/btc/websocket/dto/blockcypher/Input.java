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
@JsonPropertyOrder({ "prev_hash", "output_index", "output_value", "sequence", "addresses", "script_type", "age", "witness" })
public class Input {

    @JsonProperty("prev_hash")
    private String prevHash;

    @JsonProperty("output_index")
    private Integer outputIndex;

    @JsonProperty("output_value")
    private Integer outputValue;

    @JsonProperty("sequence")
    private Long sequence;

    @JsonProperty("addresses")
    private List<String> addresses = null;

    @JsonProperty("script_type")
    private String scriptType;

    @JsonProperty("age")
    private Integer age;

    @JsonProperty("witness")
    private List<String> witness = null;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("prev_hash")
    public String getPrevHash() {
        return prevHash;
    }

    @JsonProperty("prev_hash")
    public void setPrevHash(String prevHash) {
        this.prevHash = prevHash;
    }

    @JsonProperty("output_index")
    public Integer getOutputIndex() {
        return outputIndex;
    }

    @JsonProperty("output_index")
    public void setOutputIndex(Integer outputIndex) {
        this.outputIndex = outputIndex;
    }

    @JsonProperty("output_value")
    public Integer getOutputValue() {
        return outputValue;
    }

    @JsonProperty("output_value")
    public void setOutputValue(Integer outputValue) {
        this.outputValue = outputValue;
    }

    @JsonProperty("sequence")
    public Long getSequence() {
        return sequence;
    }

    @JsonProperty("sequence")
    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    @JsonProperty("addresses")
    public List<String> getAddresses() {
        return addresses;
    }

    @JsonProperty("addresses")
    public void setAddresses(List<String> addresses) {
        this.addresses = addresses;
    }

    @JsonProperty("script_type")
    public String getScriptType() {
        return scriptType;
    }

    @JsonProperty("script_type")
    public void setScriptType(String scriptType) {
        this.scriptType = scriptType;
    }

    @JsonProperty("age")
    public Integer getAge() {
        return age;
    }

    @JsonProperty("age")
    public void setAge(Integer age) {
        this.age = age;
    }

    @JsonProperty("witness")
    public List<String> getWitness() {
        return witness;
    }

    @JsonProperty("witness")
    public void setWitness(List<String> witness) {
        this.witness = witness;
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
            "Input{" +
            "prevHash='" +
            prevHash +
            '\'' +
            ", outputIndex=" +
            outputIndex +
            ", outputValue=" +
            outputValue +
            ", sequence=" +
            sequence +
            ", addresses=" +
            addresses +
            ", scriptType='" +
            scriptType +
            '\'' +
            ", age=" +
            age +
            ", witness=" +
            witness +
            ", additionalProperties=" +
            additionalProperties +
            '}'
        );
    }
}
