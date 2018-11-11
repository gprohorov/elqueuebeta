package com.med.controller;

import com.med.model.AwardPenaltyDTO;
import com.med.model.Salary;
import com.med.model.SalaryDTO;
import com.med.model.SalaryType;
import com.med.services.cashbox.impls.CashBoxServiceImpl;
import com.med.services.salary.impls.SalaryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;


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
@RequestMapping("/api/cashbox")
@CrossOrigin("*")
public class CashBoxController {

    @Autowired
    CashBoxServiceImpl service;
    //
    @RequestMapping("/kassa")
    public int getKassa() {
        return service.getCashBox();
    }

    @RequestMapping("/tozero/{suma}")
    public CashBox setKassaToZero(@PathVariable(value = "suma")  int suma) {

        return service.toZero(suma);
    }

    //
    @RequestMapping("/create")
    public CashBox createCash(@Valid @RequestBody CashBox cash) {
        return service.saveCash(cash);
    }


}