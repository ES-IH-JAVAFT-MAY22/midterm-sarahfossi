package com.ironhack.midtermproject.controller.interfaces.account;

import com.ironhack.midtermproject.controller.dto.account.SavingsDTO;
import com.ironhack.midtermproject.model.account.Savings;

public interface SavingsController {

    //Route to create a new savings account
    Savings createNewSavingsAccount(SavingsDTO savingsDTO);

    //Route to get a savings account by id
    //This route will let you access to all the information of this savings account,
    //including the balance
    Savings findById(Long id);
}


