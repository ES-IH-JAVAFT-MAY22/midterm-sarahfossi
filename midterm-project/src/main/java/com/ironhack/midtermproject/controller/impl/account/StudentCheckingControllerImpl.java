package com.ironhack.midtermproject.controller.impl.account;

import com.ironhack.midtermproject.controller.interfaces.account.StudentCheckingController;
import com.ironhack.midtermproject.model.account.StudentChecking;
import com.ironhack.midtermproject.repository.account.StudentCheckingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;

@RestController
public class StudentCheckingControllerImpl implements StudentCheckingController {

    @Autowired
    private StudentCheckingRepository studentCheckingRepository;

    //Route to get a student checking account by id
    //This route will let you access to all the information of this student checking
    //account, including the balance
    @GetMapping("/student-checking-accounts/{id}")
    @Override
    public StudentChecking findById(@PathVariable Long id) {
        Optional<StudentChecking> optionalStudentChecking = studentCheckingRepository.findById(id);
        return optionalStudentChecking.get();
    }
}


