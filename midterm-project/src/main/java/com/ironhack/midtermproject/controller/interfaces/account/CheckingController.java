package com.ironhack.midtermproject.controller.interfaces.account;

import com.ironhack.midtermproject.controller.dto.account.CheckingDTO;
import com.ironhack.midtermproject.model.account.Account;
import com.ironhack.midtermproject.model.account.Checking;

public interface CheckingController {

    //Route to create a new checking account
    Account createNewAccount(CheckingDTO checkingDTO);

    //Route to get a checking account by id
    //This route will let you access to all the information of this checking account, including
    //the balance.
    Checking findById(Long id);
}



