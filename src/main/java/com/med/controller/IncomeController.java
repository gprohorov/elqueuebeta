package com.med.controller;

import com.med.model.balance.Income;
import com.med.model.balance.PaymentType;
import com.med.services.income.impls.IncomeServiceImpl;
import org.json.JSONObject;
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
    public String createIncome(@Valid @RequestBody String data) {

        JSONObject jsonObj = new JSONObject(data);

        String patientId = jsonObj.getString("patientId");
        PaymentType paymentType = PaymentType.valueOf(jsonObj.getString("paymentType"));
        String desc = jsonObj.getString("desc");
        int sum = jsonObj.getInt("sum");
        int discount = jsonObj.getInt("discount");

        service.createIncome(new Income(patientId, LocalDateTime.now(), sum, paymentType, desc));
        if (discount != 0) {
            service.createIncome(new Income(patientId, LocalDateTime.now(), discount, paymentType, desc));
        }

        return JSONObject.quote("OK");
    }
/*

    public List<Talon> setAllActivity(
            @PathVariable(value = "patientId") String patientId,
            @PathVariable(value = "activity") Activity activity){
*/

}
