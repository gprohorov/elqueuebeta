package com.med.controller;

import com.med.model.balance.Income;
import com.med.model.balance.Payment;
import com.med.services.income.impls.IncomeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by george on 3/9/18.
 */
@RequestMapping("/api/income")
@RestController
@CrossOrigin("*")
public class IncomeController {


    @Autowired
    IncomeServiceImpl service ;


    @RequestMapping("/list")
    public List<Income> showIncomess(){

        return service.getAll();
    }


    // CREATE a new Income
    @PostMapping("/create")
    public Income createIncome(@Valid @RequestBody Income income) {
        income.setDateTime(LocalDateTime.now());
        //////////////// HARDCODE ///////////////
        income.setPayment(Payment.CASH);
        ///////////////////////////////////////////
        return service.createIncome(income);
    }



}
