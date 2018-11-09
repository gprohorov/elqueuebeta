package com.med.controller;

import com.med.datastorage.DataStorageTest;
import com.med.model.SalaryDTO;
import com.med.services.salary.impls.SalaryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by george on 27.04.18.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class UtilController {

     @Autowired
     DataStorageTest dataStorage;

     @Autowired
    SalaryServiceImpl salaryService;

    @GetMapping("/util/reset-db")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    public void resetPatientsTable() {

    }

    @GetMapping("/util/launch")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    public void launch() {
        // dataStorage.resetPatientsTable();

    }

    @GetMapping("/util/taskone")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    public void executeTaskOne() {

        dataStorage.taskOne();
    }

    @GetMapping("/util/tasktwo")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    public List<SalaryDTO> executeTaskTwo() {

        return dataStorage.taskTwo();
    }

    @GetMapping("/util/taskthree")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    public void executeTaskThree() {
        dataStorage.taskTwo();
    }

    @GetMapping("/util/taskfour")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    public void executeTaskFour() {
    }


}
