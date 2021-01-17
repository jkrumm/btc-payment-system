package com.jkrumm.btcpay.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.jkrumm.btcpay.domain.MerchantUser} entity.
 */
public class MerchantUserDTO implements Serializable {
    private Long id;

    private Long userId;

    private Long merchantId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MerchantUserDTO)) {
            return false;
        }

        return id != null && id.equals(((MerchantUserDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MerchantUserDTO{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", merchantId=" + getMerchantId() +
            "}";
    }
}
