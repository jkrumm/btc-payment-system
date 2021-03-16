package com.jkrumm.btcpay.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Merchant informations
 */
@Entity
@Table(name = "merchant")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Merchant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * Merchant name
     */
    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @NotNull
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "forward")
    private String forward;

    /**
     * One Merchant has One Fee associated
     */
    @OneToOne
    @JoinColumn(unique = true)
    private Fee fee;

    /**
     * One Merchant has Many Users
     */
    @OneToMany(mappedBy = "merchant")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user", "merchant" }, allowSetters = true)
    private Set<MerchantUser> merchantUsers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Merchant id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Merchant name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public Merchant email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getForward() {
        return this.forward;
    }

    public Merchant forward(String forward) {
        this.forward = forward;
        return this;
    }

    public void setForward(String forward) {
        this.forward = forward;
    }

    public Fee getFee() {
        return this.fee;
    }

    public Merchant fee(Fee fee) {
        this.setFee(fee);
        return this;
    }

    public void setFee(Fee fee) {
        this.fee = fee;
    }

    public Set<MerchantUser> getMerchantUsers() {
        return this.merchantUsers;
    }

    public Merchant merchantUsers(Set<MerchantUser> merchantUsers) {
        this.setMerchantUsers(merchantUsers);
        return this;
    }

    public Merchant addMerchantUser(MerchantUser merchantUser) {
        this.merchantUsers.add(merchantUser);
        merchantUser.setMerchant(this);
        return this;
    }

    public Merchant removeMerchantUser(MerchantUser merchantUser) {
        this.merchantUsers.remove(merchantUser);
        merchantUser.setMerchant(null);
        return this;
    }

    public void setMerchantUsers(Set<MerchantUser> merchantUsers) {
        if (this.merchantUsers != null) {
            this.merchantUsers.forEach(i -> i.setMerchant(null));
        }
        if (merchantUsers != null) {
            merchantUsers.forEach(i -> i.setMerchant(this));
        }
        this.merchantUsers = merchantUsers;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Merchant)) {
            return false;
        }
        return id != null && id.equals(((Merchant) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Merchant{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", email='" + getEmail() + "'" +
            ", forward='" + getForward() + "'" +
            "}";
    }
}
