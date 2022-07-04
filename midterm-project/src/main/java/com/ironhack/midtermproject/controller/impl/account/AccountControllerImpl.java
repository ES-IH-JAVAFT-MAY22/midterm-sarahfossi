package com.ironhack.midtermproject.controller.impl.account;

import com.ironhack.midtermproject.controller.dto.account.BalanceDTO;
import com.ironhack.midtermproject.controller.interfaces.account.AccountController;
import com.ironhack.midtermproject.service.interfaces.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
public class AccountControllerImpl implements AccountController {

    @Autowired
    private AccountService accountService;

    //Route to update the balance of any type of account
    @PatchMapping("/accounts/{id}/balance")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBalance(@PathVariable Long id, @RequestBody @Valid BalanceDTO balanceDTO) {
        accountService.updateBalance(id, balanceDTO);
    }
}

