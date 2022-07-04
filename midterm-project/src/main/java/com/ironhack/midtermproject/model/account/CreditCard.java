package com.ironhack.midtermproject.model.account;

import com.ironhack.midtermproject.model.user.AccountHolder;
import com.ironhack.midtermproject.utils.Constants;
import com.ironhack.midtermproject.utils.Money;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class CreditCard extends Account {

    @Embedded
    private Money creditLimit;

    @Positive
    @DecimalMin(value = "0.1")
    private BigDecimal interestRate;

    private LocalDate lastInterestApplicationDate = getCreationDate();

    private LocalDate creationDate;

    public CreditCard() {
    }

    public CreditCard(AccountHolder primaryOwner, AccountHolder secondaryOwner, Money balance,
                      Money penaltyFee, Money creditLimit, BigDecimal interestRate,
                      LocalDate lastInterestApplicationDate, LocalDate creationDate) {
        super(primaryOwner, secondaryOwner, balance, penaltyFee);
        this.creditLimit = creditLimit;
        this.interestRate = interestRate;
        this.lastInterestApplicationDate = lastInterestApplicationDate;
        this.creationDate = creationDate;
    }

    //Scenario 1 - The account is created with no default values
    public CreditCard(AccountHolder primaryOwner, AccountHolder secondaryOwner, Money balance,
                      Money creditLimit, BigDecimal interestRate) {
        super(primaryOwner, secondaryOwner, balance);
        setFirstCreditLimit(creditLimit);
        setInterestRate(interestRate);
    }

    //Scenario 2 - The account is created with the interest rate by default
    public CreditCard(AccountHolder primaryOwner, AccountHolder secondaryOwner, Money balance,
                      Money creditLimit) {
        super(primaryOwner, secondaryOwner, balance);
        setFirstCreditLimit(creditLimit);
        setInterestRate(Constants.CREDIT_CARD_DEFAULT_INTEREST_RATE);
    }

    //Scenario 3 - The account is created with the default credit limit
    public CreditCard(AccountHolder primaryOwner, AccountHolder secondaryOwner, Money balance,
                      BigDecimal interestRate) {
        super(primaryOwner, secondaryOwner, balance);
        setFirstCreditLimit(new Money(Constants.CREDIT_CARD_DEFAULT_CREDIT_LIMIT));
        setInterestRate(interestRate);
    }

    //Scenario 4 - The account is created with the interest rate and the credit limit by default
    public CreditCard(AccountHolder primaryOwner, AccountHolder secondaryOwner, Money balance) {
        super(primaryOwner, secondaryOwner, balance);
        setFirstCreditLimit(new Money(Constants.CREDIT_CARD_DEFAULT_CREDIT_LIMIT));
        setInterestRate(Constants.CREDIT_CARD_DEFAULT_INTEREST_RATE);
    }

    public Money getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Money creditLimit) {
        this.creditLimit = creditLimit;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public LocalDate getLastInterestApplicationDate() {
        return lastInterestApplicationDate;
    }

    public void setLastInterestApplicationDate(LocalDate lastInterestApplicationDate) {
        this.lastInterestApplicationDate = lastInterestApplicationDate;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    //CreditCards may be instantiated with a creditLimit higher than 100 but not higher than 100000
    public void setFirstCreditLimit(Money creditLimit) {
        if(creditLimit.getAmount().compareTo(new BigDecimal(100)) >= 0 ||
                creditLimit.getAmount().compareTo(new BigDecimal(100000)) <= 0) {
            this.creditLimit = creditLimit;
        } else {
            System.out.println("The credit limit cannot be higher than 100000");
        }
    }
}

