package com.ironhack.midtermproject.controller.impl.account;

import com.ironhack.midtermproject.controller.dto.account.CreditCardDTO;
import com.ironhack.midtermproject.controller.interfaces.account.CreditCardController;
import com.ironhack.midtermproject.model.account.CreditCard;
import com.ironhack.midtermproject.repository.account.CreditCardRepository;
import com.ironhack.midtermproject.service.interfaces.account.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Optional;

@RestController
public class CreditCardControllerImpl implements CreditCardController {

    @Autowired
    private CreditCardService creditCardService;

    @Autowired
    private CreditCardRepository creditCardRepository;

    //Route to create a new credit card account
    @PostMapping("/credit-cards")
    @ResponseStatus(HttpStatus.CREATED)
    public CreditCard createNewCreditCardAccount(@RequestBody @Valid CreditCardDTO creditCardDTO) {
        return creditCardService.createNewCreditCardAccount(creditCardDTO);
    }

    //Route to get a credit card account by id
    //This route will let you access to all the information of this credit card,
    //including the balance
    @GetMapping("/credit-cards/{id}")
    @Override
    public CreditCard findById(@PathVariable Long id) {
        Optional<CreditCard> optionalCreditCard = creditCardRepository.findById(id);
        return optionalCreditCard.get();
    }
}


