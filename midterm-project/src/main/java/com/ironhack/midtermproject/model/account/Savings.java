package com.ironhack.midtermproject.model.account;

import com.ironhack.midtermproject.enums.Status;
import com.ironhack.midtermproject.model.user.AccountHolder;
import com.ironhack.midtermproject.utils.Constants;
import com.ironhack.midtermproject.utils.Money;
import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Savings extends Account {

    private String secretKey;

    @Embedded
    private Money minimumBalance;

    //Savings accounts may be instantiated with an interest rate other than the default,
    // with a maximum interest rate of 0.5
    @DecimalMax(value = "0.5")
    @Positive
    private BigDecimal interestRate;

    private LocalDate creationDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDate lastInterestApplicationDate = getCreationDate();

    public Savings() {
    }

    public Savings(AccountHolder primaryOwner, AccountHolder secondaryOwner, Money balance,
                   Money penaltyFee, String secretKey, Money minimumBalance,
                   BigDecimal interestRate, LocalDate creationDate, Status status,
                   LocalDate lastInterestApplicationDate) {
        super(primaryOwner, secondaryOwner, balance, penaltyFee);
        this.secretKey = secretKey;
        this.minimumBalance = minimumBalance;
        this.interestRate = interestRate;
        this.creationDate = creationDate;
        this.status = status;
        this.lastInterestApplicationDate = lastInterestApplicationDate;
    }

    //Scenario 1 - The account is created with no default values
    public Savings(AccountHolder primaryOwner, AccountHolder secondaryOwner, Money balance,
                   String secretKey, Money minimumBalance,
                   BigDecimal interestRate) {
        super(primaryOwner, secondaryOwner, balance);
        setFirstMinimumBalance(minimumBalance);
        setInterestRate(interestRate);
    }

    //Scenario 2 - The account is created with the interest rate by default
    public Savings(AccountHolder primaryOwner, AccountHolder secondaryOwner, Money balance,
                   String secretKey, Money minimumBalance) {
        super(primaryOwner, secondaryOwner, balance);
        setFirstMinimumBalance(minimumBalance);
        setInterestRate(Constants.SAVINGS_DEFAULT_INTEREST_RATE);
    }

    //Scenario 3 - The account is created with the default minimum balance
    public Savings(AccountHolder primaryOwner, AccountHolder secondaryOwner, Money balance,
                   String secretKey, BigDecimal interestRate) {
        super(primaryOwner, secondaryOwner, balance);
        setFirstMinimumBalance(new Money(Constants.SAVINGS_DEFAULT_MIN_BALANCE));
        setInterestRate(interestRate);
    }

    //Scenario 4 - The account is created with the interest rate and the minimum balance by default
    public Savings(AccountHolder primaryOwner, AccountHolder secondaryOwner, Money balance,
                   String secretKey) {
        super(primaryOwner, secondaryOwner, balance);
        setFirstMinimumBalance(new Money(Constants.SAVINGS_DEFAULT_MIN_BALANCE));
        setInterestRate(Constants.SAVINGS_DEFAULT_INTEREST_RATE);
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

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
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

    public LocalDate getLastInterestApplicationDate() {
        return lastInterestApplicationDate;
    }

    public void setLastInterestApplicationDate(LocalDate lastInterestApplicationDate) {
        this.lastInterestApplicationDate = lastInterestApplicationDate;
    }

    //Savings accounts may be instantiated with a minimum balance of less than 1000 but no lower
    //than 100
    public void setFirstMinimumBalance(Money minimumBalance) {
        if(minimumBalance.getAmount().compareTo(new BigDecimal(100)) >= 0 ||
                minimumBalance.getAmount().compareTo(new BigDecimal(1000)) <= 0) {
            this.minimumBalance = minimumBalance;
        } else {
            System.out.println("The minimum balance cannot be lower than 100");
        }
    }
}

