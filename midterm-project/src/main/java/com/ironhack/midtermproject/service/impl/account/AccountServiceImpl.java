package com.ironhack.midtermproject.service.impl.account;

import com.ironhack.midtermproject.controller.dto.account.AccountBalanceDTO;
import com.ironhack.midtermproject.controller.dto.account.BalanceDTO;
import com.ironhack.midtermproject.model.account.Account;
import com.ironhack.midtermproject.model.account.Checking;
import com.ironhack.midtermproject.model.account.Savings;
import com.ironhack.midtermproject.repository.account.AccountRepository;
import com.ironhack.midtermproject.repository.account.CheckingRepository;
import com.ironhack.midtermproject.repository.account.SavingsRepository;
import com.ironhack.midtermproject.service.interfaces.account.AccountService;
import com.ironhack.midtermproject.utils.Constants;
import com.ironhack.midtermproject.utils.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private CheckingRepository checkingRepository;

    @Autowired
    private SavingsRepository savingsRepository;

    @Autowired
    private AccountRepository accountRepository;

    public void deducePenaltyFee(long id, AccountBalanceDTO accountBalanceDTO) {
        Optional<Checking> isCheckingAccount = checkingRepository.findById(id);
        Optional<Savings> isSavingsAccount = savingsRepository.findById(id);
        Optional<Account> isAccount = accountRepository.findById(id);

        //If it´s a checking account
        if (isCheckingAccount.isPresent()) {
            Checking checking = isCheckingAccount.get();
            checking.setBalance(accountBalanceDTO.getBalance());

            //Checking accounts should have a minimumBalance of 250
            //If the account drops below the minimumBalance, the penaltyFee should be deducted from the balance
            //automatically
            if (checking.getBalance().getAmount().compareTo(Constants.CHECKING_MINIMUM_BALANCE) == -1) {
                checking.setBalance(new Money(checking.getBalance().decreaseAmount(Constants.PENALTY_FEE)));
            }
            checkingRepository.save(checking);

        //If it´s a savings account
        } else if (isSavingsAccount.isPresent()) {
            Savings savings = isSavingsAccount.get();
            savings.setBalance(accountBalanceDTO.getBalance());

            //Savings accounts should have a default minimumBalance of 1000
            //If the account drops below the minimumBalance, the penaltyFee should be deducted from the balance
            //automatically
            if (savings.getBalance().getAmount().compareTo(Constants.SAVINGS_DEFAULT_MIN_BALANCE) == -1) {
                savings.setBalance(new Money(savings.getBalance().decreaseAmount(Constants.PENALTY_FEE)));
                }
            savingsRepository.save(savings);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        }
    }

    public void updateBalance(long id, BalanceDTO balanceDTO) {
        Optional<Checking> isCheckingAccount = checkingRepository.findById(id);
        Optional<Savings> isSavingsAccount = savingsRepository.findById(id);
        Optional<Account> isAccount = accountRepository.findById(id);

        //If the id corresponds to a checking account
        if (isCheckingAccount.isPresent()) {
            Checking checking = isCheckingAccount.get();
            checking.setBalance(balanceDTO.getBalance());

            //If the balance of this account is below the minimum balance requested, we will deduce the penalty fee
            //from the balance.
            if (checking.getBalance().getAmount().compareTo(Constants.CHECKING_MINIMUM_BALANCE) == -1) {
                checking.setBalance(new Money(checking.getBalance().decreaseAmount(Constants.PENALTY_FEE)));
            }

            checkingRepository.save(checking);

        //If the id corresponds to a savings account
        } else if (isSavingsAccount.isPresent()) {
            Savings savings = isSavingsAccount.get();
            savings.setBalance(balanceDTO.getBalance());

            //If the balance of this account is below the minimum balance requested, we will deduce the penalty fee
            //from the balance.
            if (savings.getBalance().getAmount().compareTo(Constants.SAVINGS_DEFAULT_MIN_BALANCE) == -1) {
                savings.setBalance(new Money(savings.getBalance().decreaseAmount(Constants.PENALTY_FEE)));
            }
            savingsRepository.save(savings);

            //If the id corresponds to any other type of account
        } else if (isAccount.isPresent()) {
            Account account = isAccount.get();
            account.setBalance(balanceDTO.getBalance());
            accountRepository.save(account);

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        }
    }
}

