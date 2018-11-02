package com.med.controller;

import com.med.datastorage.DataStorageTest;
import com.med.services.salary.impls.SalaryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
      salaryService.createWeekSalary();
    }

    @GetMapping("/util/launch")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    public void launch() {
        // dataStorage.resetPatientsTable();

    }

    @GetMapping("/util/taskone")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    public void executeTaskOne() {
        // dataStorage.resetPatientsTable();
        dataStorage.taskOne();
    }

    @GetMapping("/util/tasktwo")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    public void executeTaskTwo() {
        // dataStorage.resetPatientsTable();
        dataStorage.taskTwo();
    }
}
