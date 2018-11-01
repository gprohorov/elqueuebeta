package com.med.controller;

import com.med.model.*;
import com.med.services.cashbox.impls.CashBoxServiceImpl;
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

    @Autowired
    CashBoxServiceImpl cashBoxService;


    @RequestMapping("/list")
    public List<SalaryDTO> showSalaries() {

      //  service.createWeekSalary();
        return service.getSalaryList();
    }

    @RequestMapping("/create/week")
    public List<Salary> createWeek() {
        return service.createWeekSalary();
    }

    // a doctor get a salary into buzunar
    @RequestMapping("/get")
    public Salary getSalary(@Valid @RequestBody Salary salary) {
        salary.setDateTime(LocalDateTime.now());
        salary.setType(SalaryType.BUZUNAR);
        // kassa is down by this salary
        cashBoxService.saveCash(new CashBox(
                LocalDateTime.now()
                , null
                , salary.getDoctorId()
                ,null
                , -1*salary.getSum()));

        return service.createSalary(salary);
    }

    // i.e. nechay  insert bonus or a penalty
    @RequestMapping("/create")
    public Salary createSalary(@Valid @RequestBody Salary salary) {
        return service.createSalary(salary);
    }

    //      !!!       Do it!
    @RequestMapping("/set")
    public void insertPenalty(@Valid @RequestBody AwardPenaltyDTO dto) {
        System.out.println(dto);
        if(dto.getAward()!=0){

            Salary salary = new Salary(
                    dto.getDoctorID()
                    , LocalDateTime.now()
                    , SalaryType.AWARD
                    , dto.getAward());
            service.createSalary(salary);
        }

        if(dto.getPenalty()!=0){
            Salary salary = new Salary(
                    dto.getDoctorID()
                    , LocalDateTime.now()
                    , SalaryType.PENALTY
                    , dto.getPenalty());
            service.createSalary(salary);
        }

    }

}