package com.ironhack.midtermproject.controller.dto.account;

import com.ironhack.midtermproject.utils.Money;

public class AccountBalanceDTO {

    private Money balance;

    public AccountBalanceDTO() {
    }

    public AccountBalanceDTO(Money balance) {
        this.balance = balance;
    }

    public Money getBalance() {
        return balance;
    }

    public void setBalance(Money balance) {
        this.balance = balance;
    }
}
