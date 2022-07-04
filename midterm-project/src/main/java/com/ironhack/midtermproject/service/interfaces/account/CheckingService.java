package com.ironhack.midtermproject.service.interfaces.account;

import com.ironhack.midtermproject.controller.dto.account.CheckingDTO;
import com.ironhack.midtermproject.model.account.Account;
import com.ironhack.midtermproject.model.account.Checking;

public interface CheckingService {

    Account createNewAccount(CheckingDTO checkingDTO);

    Checking deduceMonthlyMaintenanceFee(long id);
}


