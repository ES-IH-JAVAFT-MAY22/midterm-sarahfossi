package com.ironhack.midtermproject.controller.impl.account;

import com.ironhack.midtermproject.controller.dto.account.SavingsDTO;
import com.ironhack.midtermproject.controller.interfaces.account.SavingsController;
import com.ironhack.midtermproject.model.account.Savings;
import com.ironhack.midtermproject.repository.account.SavingsRepository;
import com.ironhack.midtermproject.service.interfaces.account.SavingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Optional;

@RestController
public class SavingsControllerImpl implements SavingsController {

    @Autowired
    private SavingsService savingsService;

    @Autowired
    private SavingsRepository savingsRepository;

    //Route to create a new savings account
    @PostMapping("/savings-accounts")
    @ResponseStatus(HttpStatus.CREATED)
    public Savings createNewSavingsAccount(@RequestBody @Valid SavingsDTO savingsDTO) {
        return savingsService.createNewSavingsAccount(savingsDTO);
    }

    //Route to get a savings account by id
    //This route will let you access to all the information of this savings account,
    //including the balance
    @GetMapping("/savings-accounts/{id}")
    @Override
    public Savings findById(@PathVariable Long id) {
        Optional<Savings> optionalSavings = savingsRepository.findById(id);
        return optionalSavings.get();
    }
}

