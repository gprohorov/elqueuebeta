package com.med.controller;

import com.med.model.Doctor;
import com.med.model.Salary;
import com.med.model.SalaryDTO;
import com.med.services.doctor.impls.DoctorServiceImpl;
import com.med.services.salary.impls.SalaryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by george on 29.10.18.
 */

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class SalaryController {

    @Autowired
    SalaryServiceImpl service;


    @RequestMapping("/salary/list")
    public List<SalaryDTO> showSalaries() {
        return service.getSalaryList();
    }

    @RequestMapping("/salary/create/week")
    public List<Salary> createWeek() {
        return service.createWeekSalary();
    }

}