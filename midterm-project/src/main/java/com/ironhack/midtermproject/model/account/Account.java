package com.ironhack.midtermproject.model.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ironhack.midtermproject.model.user.AccountHolder;
import com.ironhack.midtermproject.utils.Money;
import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "primary_owner_id")
    private AccountHolder primaryOwner;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "secondary_owner_id")
    private AccountHolder secondaryOwner;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="amount", column= @Column(name="balance_amount")),
            @AttributeOverride(name="currency", column= @Column(name="balance_currency"))
    })
    private Money balance;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="amount", column= @Column(name="penalty_fee_amount")),
            @AttributeOverride(name="currency", column= @Column(name="penalty_fee_currency"))
    })
    private Money penaltyFee;

    public Account() {
    }

    public Account(AccountHolder primaryOwner, AccountHolder secondaryOwner, Money balance,
                   Money penaltyFee) {
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
        this.balance = balance;
        this.penaltyFee = penaltyFee;
    }

    public Account(AccountHolder primaryOwner, AccountHolder secondaryOwner, Money balance) {
        setBalance(balance);
        setPrimaryOwner(primaryOwner);
        setSecondaryOwner(secondaryOwner);
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) { this.id = id; }

    public AccountHolder getPrimaryOwner() {
        return primaryOwner;
    }

    public void setPrimaryOwner(AccountHolder primaryOwner) {
        this.primaryOwner = primaryOwner;
    }

    public AccountHolder getSecondaryOwner() {
        return secondaryOwner;
    }

    public void setSecondaryOwner(AccountHolder secondaryOwner) {
        this.secondaryOwner = secondaryOwner;
    }

    public Money getBalance() {
        return balance;
    }

    public void setBalance(Money balance) {
        this.balance = balance;
    }

    public Money getPenaltyFee() {
        return penaltyFee;
    }

    public void setPenaltyFee(Money penaltyFee) {
        this.penaltyFee = penaltyFee;
    }
}