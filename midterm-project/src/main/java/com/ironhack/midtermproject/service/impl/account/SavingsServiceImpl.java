package com.ironhack.midtermproject.service.impl.account;

import com.ironhack.midtermproject.controller.dto.account.SavingsDTO;
import com.ironhack.midtermproject.model.account.Savings;
import com.ironhack.midtermproject.model.user.AccountHolder;
import com.ironhack.midtermproject.repository.account.SavingsRepository;
import com.ironhack.midtermproject.repository.user.AccountHolderRepository;
import com.ironhack.midtermproject.service.interfaces.account.SavingsService;
import com.ironhack.midtermproject.utils.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class SavingsServiceImpl implements SavingsService {

    @Autowired
    private SavingsRepository savingsRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    //Create a new savings account
    public Savings createNewSavingsAccount(SavingsDTO savingsDTO){
        AccountHolder accountHolder = accountHolderRepository.
                findById(savingsDTO.getPrimaryOwnerId()).get();
        AccountHolder secondaryOwner;
        Money balance = savingsDTO.getBalance();
        String secretKey = savingsDTO.getSecretKey();
        Money minimumBalance = savingsDTO.getMinimumBalance();
        BigDecimal interestRate = savingsDTO.getInterestRate();

        // Savings account may have a secondary owner or not
        if (savingsDTO.getSecondaryOwnerId() != null) {
            secondaryOwner = accountHolderRepository.findById(savingsDTO.getSecondaryOwnerId()).get();
        } else {
            secondaryOwner = null;
        }

        //Scenario 1 - The account is created with no default values
        if(minimumBalance != null && interestRate != null) {
            Savings savings = new Savings(accountHolder, secondaryOwner, balance, secretKey,
                    minimumBalance, interestRate);
            savingsRepository.save(savings);
            return savings;

        //Scenario 2 - The account is created with the interest rate by default
        } else if(minimumBalance != null) {
            Savings savings = new Savings(accountHolder, secondaryOwner, balance, secretKey,
                    minimumBalance);
            savingsRepository.save(savings);
            return savings;

        //Scenario 3 - The account is created with the default minimum balance
        } else if(interestRate != null) {
            Savings savings = new Savings(accountHolder, secondaryOwner, balance, secretKey,
                    interestRate);
            savingsRepository.save(savings);
            return savings;

        //Scenario 4 - The account is created with the interest rate and the minimum balance by
        //default
        } else {
            Savings savings = new Savings(accountHolder, secondaryOwner, balance, secretKey);
            savingsRepository.save(savings);
            return savings;
        }
    }

    //Adding interest on savings accounts. This interest is added to this type of account annually at the rate
    //of specified interestRate per year.
    //When a savings account balance is accessed, we have to determinate if it has been 1 year or more since
    //interest was added to the account, and add the appropriate interest to the balance if necessary.
    public Savings addInterest(long id) {
        Optional<Savings> existsSavings = savingsRepository.findById(id);

        LocalDate lastInterestApplicationDate = existsSavings.get().
                getLastInterestApplicationDate();
        LocalDate expectedDate = LocalDate.of(lastInterestApplicationDate.getYear() + 1,
                lastInterestApplicationDate.getMonth(), lastInterestApplicationDate.
                        getDayOfMonth());

        if (existsSavings.isPresent()) {

            //If a year has passed since the lastInterestApplicationDate
            if (expectedDate.compareTo(LocalDate.now()) <= 0) {
                Savings savingsAccount = existsSavings.get();

                //How many years have passed since the last time the interest rate was applied?
                int years = LocalDate.now().getYear() - lastInterestApplicationDate.getYear();

                //The current balance amount is multiplied by the interest rate and by the number of years
                savingsAccount.setBalance(new Money(savingsAccount.getBalance()
                        .increaseAmount(savingsAccount.getBalance().getAmount()
                                .multiply(savingsAccount.getInterestRate())
                                .multiply(new BigDecimal(years)))));

                //The moment this action is performed becomes the last time the interest rate was applied
                savingsAccount.setLastInterestApplicationDate(LocalDate.now());

                savingsRepository.save(savingsAccount);
                return savingsAccount;

            } else {
                return existsSavings.get();
            }

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Savings account not found");
        }
    }
}



