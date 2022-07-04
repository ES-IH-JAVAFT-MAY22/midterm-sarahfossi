package com.ironhack.midtermproject.controller.impl.account;

import com.ironhack.midtermproject.controller.dto.account.CheckingDTO;
import com.ironhack.midtermproject.controller.interfaces.account.CheckingController;
import com.ironhack.midtermproject.model.account.Account;
import com.ironhack.midtermproject.model.account.Checking;
import com.ironhack.midtermproject.repository.account.CheckingRepository;
import com.ironhack.midtermproject.service.interfaces.account.CheckingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Optional;

@RestController
public class CheckingControllerImpl implements CheckingController {

    @Autowired
    private CheckingService checkingService;

    @Autowired
    private CheckingRepository checkingRepository;

    //Route to create a new checking account
    @PostMapping("/accounts")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createNewAccount(@RequestBody @Valid CheckingDTO checkingDTO) {
        return checkingService.createNewAccount(checkingDTO);
    }

    //Route to get a checking account by id
    //This route will let you access to all the information of this checking account, including
    //the balance.
    @GetMapping("/checking-accounts/{id}")
    @Override
    public Checking findById(@PathVariable Long id) {
        Optional<Checking> optionalChecking = checkingRepository.findById(id);
        return optionalChecking.get();
    }
}



