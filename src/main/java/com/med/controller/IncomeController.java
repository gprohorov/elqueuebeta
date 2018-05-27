package com.med.controller;

import com.med.model.Income;
import com.med.services.income.impls.IncomeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    @PostMapping("/income/create")
    public Income createIncome(@Valid @RequestBody Income income) {

        return service.createIncome(income);
    }



}
