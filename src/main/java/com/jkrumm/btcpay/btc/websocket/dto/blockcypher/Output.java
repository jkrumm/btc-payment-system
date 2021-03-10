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
@JsonPropertyOrder({ "value", "script", "addresses", "script_type", "spent_by" })
public class Output {

    @JsonProperty("value")
    private Integer value;

    @JsonProperty("script")
    private String script;

    @JsonProperty("addresses")
    private List<String> addresses = null;

    @JsonProperty("script_type")
    private String scriptType;

    @JsonProperty("spent_by")
    private String spentBy;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("value")
    public Integer getValue() {
        return value;
    }

    @JsonProperty("value")
    public void setValue(Integer value) {
        this.value = value;
    }

    @JsonProperty("script")
    public String getScript() {
        return script;
    }

    @JsonProperty("script")
    public void setScript(String script) {
        this.script = script;
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

    @JsonProperty("spent_by")
    public String getSpentBy() {
        return spentBy;
    }

    @JsonProperty("spent_by")
    public void setSpentBy(String spentBy) {
        this.spentBy = spentBy;
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
            "Output{" +
            "value=" +
            value +
            ", script='" +
            script +
            '\'' +
            ", addresses=" +
            addresses +
            ", scriptType='" +
            scriptType +
            '\'' +
            ", spentBy='" +
            spentBy +
            '\'' +
            ", additionalProperties=" +
            additionalProperties +
            '}'
        );
    }
}
