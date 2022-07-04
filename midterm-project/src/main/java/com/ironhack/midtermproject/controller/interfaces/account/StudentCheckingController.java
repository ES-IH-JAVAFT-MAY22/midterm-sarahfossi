package com.ironhack.midtermproject.controller.interfaces.account;

import com.ironhack.midtermproject.model.account.StudentChecking;

public interface StudentCheckingController {

    //Route to get a student checking account by id
    //This route will let you access to all the information of this student checking
    //account, including the balance
    StudentChecking findById(Long id);
}
