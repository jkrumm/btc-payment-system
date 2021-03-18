package com.jkrumm.btcpay.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.jkrumm.btcpay.domain.MerchantUser} entity.
 */
public class MerchantUserDTO implements Serializable {

    private Long id;

    private UserDTO user;

    private MerchantDTO merchant;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public MerchantDTO getMerchant() {
        return merchant;
    }

    public void setMerchant(MerchantDTO merchant) {
        this.merchant = merchant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MerchantUserDTO)) {
            return false;
        }

        MerchantUserDTO merchantUserDTO = (MerchantUserDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, merchantUserDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MerchantUserDTO{" +
            "id=" + getId() +
            ", user=" + getUser() +
            ", merchant=" + getMerchant() +
            "}";
    }
}
