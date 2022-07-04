package com.ironhack.midtermproject.controller.dto.account;

import com.ironhack.midtermproject.utils.Money;

public class CheckingDTO{

    private Long primaryOwnerId;

    private Long secondaryOwnerId;

    private Money balance;

    private String secretKey;

    public CheckingDTO() {
    }

    public CheckingDTO(Long primaryOwnerId, Long secondaryOwnerId, Money balance, String secretKey) {
        this.primaryOwnerId = primaryOwnerId;
        this.secondaryOwnerId = secondaryOwnerId;
        this.balance = balance;
        this.secretKey = secretKey;
    }

    public Long getPrimaryOwnerId() {
        return primaryOwnerId;
    }

    public void setPrimaryOwnerId(Long primaryOwnerId) {
        this.primaryOwnerId = primaryOwnerId;
    }

    public Long getSecondaryOwnerId() {
        return secondaryOwnerId;
    }

    public void setSecondaryOwnerId(Long secondaryOwnerId) {
        this.secondaryOwnerId = secondaryOwnerId;
    }

    public Money getBalance() {
        return balance;
    }

    public void setBalance(Money balance) {
        this.balance = balance;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
