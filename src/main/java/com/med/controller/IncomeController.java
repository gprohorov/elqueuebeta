package com.med.controller;

import com.med.model.balance.Income;
import com.med.model.balance.PaymentType;
import com.med.services.income.impls.IncomeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
 //   public Income createIncome(@Valid @RequestBody Income income) {
    public Income createIncome(
            @PathVariable(value = "patientId") String patientId,
            @PathVariable(value = "sum")       int sum,
            @PathVariable(value = "discount")  int discount,
            @PathVariable(value = "type")      PaymentType type,
            @PathVariable(value = "desc")      String desc
    ) {
        Income income = new Income(patientId, LocalDateTime.now(), sum, type, desc);
        if (discount!=0){
            Income dscnt = new Income(patientId, LocalDateTime.now(), discount, PaymentType.DISCOUNT, "");
            service.createIncome(dscnt);
        }

        return service.createIncome(income);
    }
/*

    public List<Talon> setAllActivity(
            @PathVariable(value = "patientId") String patientId,
            @PathVariable(value = "activity") Activity activity){
*/

}
