package com.med.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.med.datastorage.DataStorageTest;
import com.med.model.SalaryDTO;
import com.med.services.SalaryService;

@RestController
@RequestMapping("/api/util")
@CrossOrigin("*")
public class UtilController {

	@Autowired
    DataStorageTest dataStorage;

    @Autowired
    SalaryService salaryService;

    @GetMapping("/reset-db")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    public void resetPatientsTable() { }

    @GetMapping("/launch")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    public void launch() {
        // dataStorage.resetPatientsTable();
    }

    @GetMapping("/taskone")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    public void executeTaskOne() {
       // dataStorage.taskOne();
    }

    @GetMapping("/tasktwo")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    public List<SalaryDTO> executeTaskTwo() {
    	// return dataStorage.taskTwo();
        return null;
    }

    @GetMapping("/taskthree")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    public void executeTaskThree() {
        dataStorage.taskTree(5);
    }

    @GetMapping("/taskfour")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    public void executeTaskFour() { }
}