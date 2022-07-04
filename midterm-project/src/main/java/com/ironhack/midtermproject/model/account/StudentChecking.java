package com.ironhack.midtermproject.model.account;

import com.ironhack.midtermproject.enums.Status;
import com.ironhack.midtermproject.model.user.AccountHolder;
import com.ironhack.midtermproject.utils.Money;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrimaryKeyJoinColumn;
import java.time.LocalDate;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class StudentChecking extends Account {

    private String secretKey;

    private LocalDate creationDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    public StudentChecking() {
    }

    public StudentChecking(AccountHolder primaryOwner, AccountHolder secondaryOwner, Money balance,
                           Money penaltyFee, String secretKey, LocalDate creationDate,
                           Status status) {
        super(primaryOwner, secondaryOwner, balance, penaltyFee);
        this.secretKey = secretKey;
        this.creationDate = creationDate;
        this.status = status;
    }

    public StudentChecking(AccountHolder primaryOwner, AccountHolder secondaryOwner, Money balance,
                           String secretKey) {
        super(primaryOwner, secondaryOwner, balance);
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
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
}
