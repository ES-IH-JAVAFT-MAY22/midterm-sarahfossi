package com.ironhack.midtermproject.service.interfaces.account;

import com.ironhack.midtermproject.controller.dto.account.CreditCardDTO;
import com.ironhack.midtermproject.model.account.CreditCard;

public interface CreditCardService {

    CreditCard createNewCreditCardAccount(CreditCardDTO creditCardDTO);
    CreditCard addInterest(long id);
}
