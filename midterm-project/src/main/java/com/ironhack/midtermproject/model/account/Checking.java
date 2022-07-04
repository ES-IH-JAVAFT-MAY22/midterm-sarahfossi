package com.ironhack.midtermproject.model.account;

import com.ironhack.midtermproject.enums.Status;
import com.ironhack.midtermproject.model.user.AccountHolder;
import com.ironhack.midtermproject.utils.Money;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Checking extends Account {

    private String secretKey;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="amount", column= @Column(name="minimum_balance_amount")),
            @AttributeOverride(name="currency", column= @Column(name="minimum_balance_currency"))
    })
    private Money minimumBalance;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="amount", column= @Column(name="monthly_maintenance_fee_amount")),
            @AttributeOverride(name="currency", column= @Column(name="monthly_maintenance_fee_currency"))
    })
    private Money monthlyMaintenanceFee;

    private LocalDate creationDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDate lastMaintenanceFeeApplicationDate = getCreationDate();

    public Checking() {
    }

    public Checking(AccountHolder primaryOwner, AccountHolder secondaryOwner, Money balance,
                    Money penaltyFee, String secretKey, Money minimumBalance,
                    Money monthlyMaintenanceFee, LocalDate creationDate, Status status,
                    LocalDate lastMaintenanceFeeApplicationDate) {
        super(primaryOwner, secondaryOwner, balance, penaltyFee);
        this.secretKey = secretKey;
        this.minimumBalance = minimumBalance;
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
        this.creationDate = creationDate;
        this.status = status;
        this.lastMaintenanceFeeApplicationDate = lastMaintenanceFeeApplicationDate;
    }

    //Checking accounts should have a minimumBalance of 250 and a monthlyMaintenanceFee of 12
    public Checking(AccountHolder primaryOwner, AccountHolder secondaryOwner, Money balance,
                    String secretKey) {
        super(primaryOwner, secondaryOwner, balance);
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Money getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(Money minimumBalance) {
        this.minimumBalance = minimumBalance;
    }

    public Money getMonthlyMaintenanceFee() {
        return monthlyMaintenanceFee;
    }

    public void setMonthlyMaintenanceFee(Money monthlyMaintenanceFee) {
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getLastMaintenanceFeeApplicationDate() {
        return lastMaintenanceFeeApplicationDate;
    }

    public void setLastMaintenanceFeeApplicationDate(LocalDate lastMaintenanceFeeApplicationDate) {
        this.lastMaintenanceFeeApplicationDate = lastMaintenanceFeeApplicationDate;
    }
}

