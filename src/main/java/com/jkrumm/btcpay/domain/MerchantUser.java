package com.jkrumm.btcpay.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MerchantUser.
 */
@Entity
@Table(name = "merchant_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MerchantUser implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @ManyToOne
    @JsonIgnoreProperties(value = "merchantUsers", allowSetters = true)
    private Merchant merchant;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public MerchantUser user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public MerchantUser merchant(Merchant merchant) {
        this.merchant = merchant;
        return this;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MerchantUser)) {
            return false;
        }
        return id != null && id.equals(((MerchantUser) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MerchantUser{" +
            "id=" + getId() +
            "}";
    }
}
