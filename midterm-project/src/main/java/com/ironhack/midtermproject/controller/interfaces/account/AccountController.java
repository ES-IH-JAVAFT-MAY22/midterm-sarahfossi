package com.ironhack.midtermproject.controller.interfaces.account;

import com.ironhack.midtermproject.controller.dto.account.BalanceDTO;

public interface AccountController {

    //Route to update the balance of any type of account
    void updateBalance(Long id, BalanceDTO balanceDTO);
}


