package com.ironhack.midtermproject.service.impl.account;

import com.ironhack.midtermproject.controller.dto.account.CreditCardDTO;
import com.ironhack.midtermproject.model.account.CreditCard;
import com.ironhack.midtermproject.model.user.AccountHolder;
import com.ironhack.midtermproject.repository.account.CreditCardRepository;
import com.ironhack.midtermproject.repository.user.AccountHolderRepository;
import com.ironhack.midtermproject.service.interfaces.account.CreditCardService;
import com.ironhack.midtermproject.utils.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class CreditCardServiceImpl implements CreditCardService {

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    //Create a new credit card account
    public CreditCard createNewCreditCardAccount(CreditCardDTO creditCardDTO) {
        AccountHolder accountHolder = accountHolderRepository.
                findById(creditCardDTO.getPrimaryOwnerId()).get();
        AccountHolder secondaryOwner;
        Money balance = creditCardDTO.getBalance();
        Money creditLimit = creditCardDTO.getCreditLimit();
        BigDecimal interestRate = creditCardDTO.getInterestRate();

        // Credit card account may have a secondary owner or not
        if (creditCardDTO.getSecondaryOwnerId() != null) {
            secondaryOwner = accountHolderRepository.findById(creditCardDTO.getSecondaryOwnerId())
                            .get();
        } else {
            secondaryOwner = null;
        }

        //Scenario 1 - The account is created with no default values
        if (creditLimit != null && interestRate != null) {
            CreditCard creditCard = new CreditCard(accountHolder, secondaryOwner, balance,
                    creditLimit, interestRate);
            creditCardRepository.save(creditCard);
            return creditCard;

            //Scenario 2 - The account is created with the interest rate by default
        } else if (creditLimit != null) {
            CreditCard creditCard = new CreditCard(accountHolder, secondaryOwner, balance,
                    creditLimit);
            creditCardRepository.save(creditCard);
            return creditCard;

            //Scenario 3 - The account is created with the default credit limit
        } else if (interestRate != null) {
            CreditCard creditCard = new CreditCard(accountHolder, secondaryOwner, balance,
                    interestRate);
            creditCardRepository.save(creditCard);
            return creditCard;

            //Scenario 4 - The account is created with the interest rate and the credit limit by
            //default
        } else {
            CreditCard creditCard = new CreditCard(accountHolder, secondaryOwner, balance);
            creditCardRepository.save(creditCard);
            return creditCard;
        }
    }

    //Interest on credit cards is added to the balance monthly. When the balance of a credit card is accessed,
    // we determine if it has been 1 month or more since the account was created or since interested was added,
    //and if so, the appropriate interest is added to the balance.
    public CreditCard addInterest(long id) {

        Optional<CreditCard> existsCreditCard = creditCardRepository.findById(id);
        LocalDate lastInterestApplicationDate = existsCreditCard.get().getLastInterestApplicationDate();
        LocalDate expectedDate;

        if(lastInterestApplicationDate.getMonthValue()+1 > 12) {
            expectedDate = LocalDate.of(lastInterestApplicationDate.getYear()+1,
                    01, lastInterestApplicationDate.getDayOfMonth());
        } else {
            expectedDate = LocalDate.of(lastInterestApplicationDate.getYear(),
                    lastInterestApplicationDate.getMonthValue()+1, lastInterestApplicationDate.getDayOfMonth());
        }

        if (existsCreditCard.isPresent()) {
            //If at least one month has passed since the last time the interests were applied
            if (expectedDate.compareTo(LocalDate.now()) <= 0) {
                CreditCard creditCardAccount = existsCreditCard.get();
                //Number of months that have passed since the last time the interest rate was applied
                int months;

                //Different procedures if the date of the last time we applied the interests was this year or
                // if it was another year
                if(LocalDate.now().getYear() == lastInterestApplicationDate.getYear()) {
                    months = LocalDate.now().getMonthValue() - lastInterestApplicationDate.getMonthValue();
                } else {
                    int i = 0;
                    while(LocalDate.now().compareTo(lastInterestApplicationDate) > 0){
                        if(lastInterestApplicationDate.getMonthValue()+1 > 12) {
                            lastInterestApplicationDate = LocalDate.of(lastInterestApplicationDate
                                            .getYear()+1, 1, lastInterestApplicationDate
                                    .getDayOfMonth());
                        } else {
                            lastInterestApplicationDate = LocalDate.of(lastInterestApplicationDate.getYear(),
                                    lastInterestApplicationDate.getMonthValue()+1,
                                    lastInterestApplicationDate.getDayOfMonth());
                        }
                        i++;
                    }
                    months = i;
                }

                //The current balance amount is multiplied by the interest rate and by the number of months
                creditCardAccount.setBalance(new Money(creditCardAccount.getBalance()
                        .increaseAmount(creditCardAccount.getBalance().getAmount()
                                .multiply(creditCardAccount.getInterestRate())
                                .multiply(new BigDecimal(months)))));

                // The moment this action is performed becomes the last time the interest rate was applied
                creditCardAccount.setLastInterestApplicationDate(LocalDate.now());

                creditCardRepository.save(creditCardAccount);
                return creditCardAccount;
            } else {
                return existsCreditCard.get();
            }

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Credit card account not found");
        }
    }
}
