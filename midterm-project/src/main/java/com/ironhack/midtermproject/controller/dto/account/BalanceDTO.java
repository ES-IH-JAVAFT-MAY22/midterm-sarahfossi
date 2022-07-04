package com.ironhack.midtermproject.controller.dto.account;

import com.ironhack.midtermproject.utils.Money;

public class BalanceDTO {

    private Money balance;

    public BalanceDTO() {
    }

    public BalanceDTO(Money balance) {
        this.balance = balance;
    }

    public Money getBalance() {
        return balance;
    }

    public void setBalance(Money balance) {
        this.balance = balance;
    }
}
