package com.med.controller;

import com.med.model.*;
import com.med.services.doctor.impls.DoctorServiceImpl;
import com.med.services.salary.impls.SalaryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by george on 29.10.18.
 */

@RestController
@RequestMapping("/api/salary")
@CrossOrigin("*")
public class SalaryController {

    @Autowired
    SalaryServiceImpl service;


    @RequestMapping("/list")
    public List<SalaryDTO> showSalaries() {

        service.createWeekSalary();
        return service.getSalaryList();
    }

    @RequestMapping("/salary/create/week")
    public List<Salary> createWeek() {
        return service.createWeekSalary();
    }

    // a doctor get a salary into buzunar
    @RequestMapping("/get")
    public Salary getSalary(@Valid @RequestBody Salary salary) {
        salary.setDateTime(LocalDateTime.now());
        salary.setType(SalaryType.BUZUNAR);
        return service.createSalary(salary);
    }

    // i.e. nechay  insert bonus or a penalty
    @RequestMapping("/create")
    public Salary createSalary(@Valid @RequestBody Salary salary) {
        return service.createSalary(salary);
    }

    @RequestMapping("/set")
    public Salary insertPenalty(@Valid @RequestBody AwardPenaltyDTO dto) {
        return null;
    }

}