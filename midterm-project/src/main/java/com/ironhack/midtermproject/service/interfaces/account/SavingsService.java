package com.ironhack.midtermproject.service.interfaces.account;

import com.ironhack.midtermproject.controller.dto.account.SavingsDTO;
import com.ironhack.midtermproject.model.account.Savings;

public interface SavingsService {

    Savings createNewSavingsAccount(SavingsDTO savingsDTO);

    Savings addInterest(long id);
}
