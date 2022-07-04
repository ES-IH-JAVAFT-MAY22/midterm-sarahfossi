package com.ironhack.midtermproject.model.user;

import com.ironhack.midtermproject.utils.Address;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class AccountHolder extends User {

    private LocalDate dateOfBirth;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="streetName", column= @Column(name="primary_street_name")),
            @AttributeOverride(name="streetNumber", column= @Column(name="primary_street_number")),
            @AttributeOverride(name="city", column= @Column(name="primary_city")),
            @AttributeOverride(name="country", column= @Column(name="primary_country")),
            @AttributeOverride(name="postalCode", column= @Column(name="primary_postal_code"))
    })
    private Address primaryAddress;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="streetName", column= @Column(name="mailing_street_name")),
            @AttributeOverride(name="streetNumber", column= @Column(name="mailing_street_number")),
            @AttributeOverride(name="city", column= @Column(name="mailing_city")),
            @AttributeOverride(name="country", column= @Column(name="mailing_country")),
            @AttributeOverride(name="postalCode", column= @Column(name="mailing_postal_code"))
    })
    private Address mailingAddress;

    public AccountHolder() {
    }

    public AccountHolder(String name, String username, String password, LocalDate dateOfBirth, Address primaryAddress,
                         Address mailingAddress) {
        super(name, username, password);
        this.dateOfBirth = dateOfBirth;
        this.primaryAddress = primaryAddress;
        this.mailingAddress = mailingAddress;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Address getPrimaryAddress() {
        return primaryAddress;
    }

    public void setPrimaryAddress(Address primaryAddress) {
        this.primaryAddress = primaryAddress;
    }

    public Address getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(Address mailingAddress) {
        this.mailingAddress = mailingAddress;
    }
}