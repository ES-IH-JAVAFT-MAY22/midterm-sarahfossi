package com.ironhack.midtermproject.service.impl.account;

import com.ironhack.midtermproject.controller.dto.account.CheckingDTO;
import com.ironhack.midtermproject.model.account.*;
import com.ironhack.midtermproject.model.user.AccountHolder;
import com.ironhack.midtermproject.repository.account.CheckingRepository;
import com.ironhack.midtermproject.repository.account.StudentCheckingRepository;
import com.ironhack.midtermproject.repository.user.AccountHolderRepository;
import com.ironhack.midtermproject.service.interfaces.account.CheckingService;
import com.ironhack.midtermproject.utils.Constants;
import com.ironhack.midtermproject.utils.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class CheckingServiceImpl implements CheckingService {

    @Autowired
    private CheckingRepository checkingRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private StudentCheckingRepository studentCheckingRepository;

    //Create a new checking account
    public Account createNewAccount(CheckingDTO checkingDTO) {

    //When creating a new Checking account, if the primaryOwner is less than 24, a StudentChecking
    // account should be created otherwise a regular Checking Account should be created.
        if(LocalDate.now().getYear() -
                accountHolderRepository.findById(checkingDTO.getPrimaryOwnerId()).get()
                        .getDateOfBirth().getYear() < 24) {
            AccountHolder accountHolder = accountHolderRepository.
                    findById(checkingDTO.getPrimaryOwnerId()).get();
            AccountHolder secondaryOwner;
            Money balance = checkingDTO.getBalance();
            String secretKey = checkingDTO.getSecretKey();

            //Student checking account may have a secondary owner or not
            if(checkingDTO.getSecondaryOwnerId() != null) {
                secondaryOwner = accountHolderRepository.findById(checkingDTO.getSecondaryOwnerId())
                        .get();
            } else {
                secondaryOwner = null;
            }

            StudentChecking studentChecking = new StudentChecking(accountHolder, secondaryOwner,
                    balance, secretKey);
            studentCheckingRepository.save(studentChecking);
            return studentChecking;

        } else {

            AccountHolder accountHolder = accountHolderRepository.
                    findById(checkingDTO.getPrimaryOwnerId()).get();
            AccountHolder secondaryOwner;
            Money balance = checkingDTO.getBalance();
            String secretKey = checkingDTO.getSecretKey();

            //Student checking account may have a secondary owner or not
            if(checkingDTO.getSecondaryOwnerId() != null) {
                secondaryOwner = accountHolderRepository.findById(checkingDTO
                        .getSecondaryOwnerId()).get();
            } else {
                secondaryOwner = null;
            }

            Checking checking = new Checking(accountHolder, secondaryOwner, balance, secretKey);
            checkingRepository.save(checking);
            return checking;
        }
    }

    public Checking deduceMonthlyMaintenanceFee(long id) {
        Optional<Checking> existsChecking = checkingRepository.findById(id);
        LocalDate lastMaintenanceFeeApplicationDate = existsChecking.get()
                .getLastMaintenanceFeeApplicationDate();
        LocalDate expectedDate;

        if(lastMaintenanceFeeApplicationDate.getMonthValue()+1 > 12) {
            expectedDate = LocalDate.of(lastMaintenanceFeeApplicationDate.getYear()+1,
                    01, lastMaintenanceFeeApplicationDate.getDayOfMonth());
        } else {
            expectedDate = LocalDate.of(lastMaintenanceFeeApplicationDate.getYear(),
                    lastMaintenanceFeeApplicationDate.getMonthValue()+1,
                    lastMaintenanceFeeApplicationDate.getDayOfMonth());
        }

        if (existsChecking.isPresent()) {
            //If at least one month has passed since the last time the maintenance fee was applied
            if (expectedDate.compareTo(LocalDate.now()) <= 0) {
                Checking checkingAccount = existsChecking.get();
                //Number of months that have passed since the last time the maintenance fee was applied
                int months;

                //Different procedures if the date of the last time we applied the maintenance fee was this
                // year or if it was another year
                if(LocalDate.now().getYear() == lastMaintenanceFeeApplicationDate.getYear()) {
                    months = LocalDate.now().getMonthValue() - lastMaintenanceFeeApplicationDate
                            .getMonthValue();
                } else {
                    int i = 0;
                    while(LocalDate.now().compareTo(lastMaintenanceFeeApplicationDate) > 0){
                        if(lastMaintenanceFeeApplicationDate.getMonthValue()+1 > 12) {
                            lastMaintenanceFeeApplicationDate =
                                    LocalDate.of(lastMaintenanceFeeApplicationDate
                                    .getYear()+1, 1, lastMaintenanceFeeApplicationDate
                                    .getDayOfMonth());
                        } else {
                            lastMaintenanceFeeApplicationDate = LocalDate.of(lastMaintenanceFeeApplicationDate
                                            .getYear(),
                                    lastMaintenanceFeeApplicationDate.getMonthValue()+1,
                                    lastMaintenanceFeeApplicationDate.getDayOfMonth());
                        }
                        i++;
                    }
                    months = i;
                }

                //We deduce to the current balance the maintenance fee multiplied by the number of months
                checkingAccount.setBalance(new Money(checkingAccount.getBalance()
                        .decreaseAmount(Constants
                                .CHECKING_MONTHLY_MAINTENANCE_FEE
                                .multiply(new BigDecimal(months)))));

                //The moment this action is performed becomes the last time the maintenance fee was applied
                checkingAccount.setLastMaintenanceFeeApplicationDate(LocalDate.now());

                checkingRepository.save(checkingAccount);
                return checkingAccount;

            } else {
                return existsChecking.get();
            }

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Checking account not found");
        }
    }
}


