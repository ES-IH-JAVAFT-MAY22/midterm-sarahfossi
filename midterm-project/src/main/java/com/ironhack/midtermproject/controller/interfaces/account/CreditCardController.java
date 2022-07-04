package com.ironhack.midtermproject.controller.interfaces.account;

import com.ironhack.midtermproject.controller.dto.account.CreditCardDTO;
import com.ironhack.midtermproject.model.account.CreditCard;

public interface CreditCardController {

    //Route to create a new credit card account
    CreditCard createNewCreditCardAccount(CreditCardDTO creditCardDTO);

    //Route to get a credit card account by id
    //This route will let you access to all the information of this credit card,
    //including the balance
    CreditCard findById(Long id);
}



