package com.ironhack.midtermproject.service.interfaces.account;

import com.ironhack.midtermproject.controller.dto.account.AccountBalanceDTO;
import com.ironhack.midtermproject.controller.dto.account.BalanceDTO;

public interface AccountService {

    void deducePenaltyFee(long id, AccountBalanceDTO accountBalanceDTO);

    void updateBalance(long id, BalanceDTO balanceDTO);
}


