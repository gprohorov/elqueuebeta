package com.med.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.med.model.CashBox;
import com.med.model.CashType;
import com.med.model.balance.Accounting;
import com.med.model.balance.PaymentType;
import com.med.services.AccountingService;
import com.med.services.CashBoxService;
import com.med.services.PatientService;
import com.med.services.UserService;

@RequestMapping("/api/accounting")
@RestController
@CrossOrigin("*")
public class AccountingController {

    @Autowired
    AccountingService service;

    @Autowired
    UserService userService;

    @Autowired
    PatientService patientService;

    @Autowired
    CashBoxService cashBoxService;

    @RequestMapping("/list")
    public List<Accounting> showAll() {
        return service.getAll();
    }

    // CREATE a new Accounting
    @PostMapping("/create")
    public String create(@Valid @RequestBody String data) {

        JSONObject jsonObj = new JSONObject(data);

        String patientId = jsonObj.getString("patientId");
        PaymentType paymentType = PaymentType.valueOf(jsonObj.getString("paymentType"));
        String desc = jsonObj.getString("desc") + " " + paymentType.toString();
        int sum = jsonObj.getInt("sum");
        int discount = jsonObj.getInt("discount");
        boolean closeDay = jsonObj.getBoolean("closeDay");
      
        int doctorId = 1;

        if (sum != 0) {
            service.createAccounting(new Accounting(
        	    	doctorId,
                    patientId,
                    LocalDateTime.now(),
                    null,
                    sum,
                    paymentType,
                    desc));
            if (paymentType.equals(PaymentType.CASH) || paymentType.equals(PaymentType.CHECK)) {
                CashBox cash = new CashBox(LocalDateTime.now(),
                        patientId,
                        0,
                        desc,
                        sum);
                cash.setType(CashType.PATIENT);
                cashBoxService.createCash(cash);
            }
        }

        if (discount != 0) {
            service.createAccounting(new Accounting(
        		doctorId, patientId, LocalDateTime.now(), null, discount, PaymentType.DISCOUNT, desc));
        }
        
        if (closeDay) {
            patientService.patientBye(patientId);
        }

        return JSONObject.quote("OK");
    }
}
