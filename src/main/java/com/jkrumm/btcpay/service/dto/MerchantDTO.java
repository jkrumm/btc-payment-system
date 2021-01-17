package com.jkrumm.btcpay.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.jkrumm.btcpay.domain.Merchant} entity.
 */
@ApiModel(description = "Merchant informations")
public class MerchantDTO implements Serializable {
    private Long id;

    /**
     * Merchant name
     */
    @NotNull
    @ApiModelProperty(value = "Merchant name", required = true)
    private String name;

    @NotNull
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    private String email;

    /**
     * One Merchant has One Fee associated
     */
    @ApiModelProperty(value = "One Merchant has One Fee associated")
    private Long feeId;

    /**
     * One Merchant has Many Users
     */
    @ApiModelProperty(value = "One Merchant has Many Users")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getFeeId() {
        return feeId;
    }

    public void setFeeId(Long feeId) {
        this.feeId = feeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MerchantDTO)) {
            return false;
        }

        return id != null && id.equals(((MerchantDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MerchantDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", email='" + getEmail() + "'" +
            ", feeId=" + getFeeId() +
            "}";
    }
}
