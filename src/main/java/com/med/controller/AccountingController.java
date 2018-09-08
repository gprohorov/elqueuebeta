package com.med.controller;

import com.med.model.balance.Accounting;
import com.med.model.balance.PaymentType;
import com.med.services.accounting.impls.AccountingServiceImpl;
import com.med.services.patient.Impls.PatientServiceImpl;
import com.med.services.user.UserService;
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
public class AccountingController {


    @Autowired
    AccountingServiceImpl service ;

    @Autowired
    UserService userService;

    @Autowired
    PatientServiceImpl patientService;


    @RequestMapping("/list")
    public List<Accounting> showAll(){

        return service.getAll();
    }


    // CREATE a new Accounting
    @PostMapping("/create")
    public String create(@Valid @RequestBody String data) {

        JSONObject jsonObj = new JSONObject(data);


        String patientId = jsonObj.getString("patientId");
        PaymentType paymentType = PaymentType.valueOf(jsonObj.getString("paymentType"));
        String desc = jsonObj.getString("desc");
        int sum = jsonObj.getInt("sum");
        int discount = jsonObj.getInt("discount");
        boolean closeDay = jsonObj.getBoolean("closeDay");
      
        int doctorId = 1;

        if (sum != 0) {
            service.createAccounting(new Accounting(doctorId, patientId, LocalDateTime.now(), null,  sum, paymentType, desc));
        }

        if (discount != 0) {
            service.createAccounting(new Accounting(doctorId, patientId, LocalDateTime.now(), null, discount, PaymentType.DISCOUNT, desc));
        }
        
      if (closeDay) {
            patientService.patientBye(patientId);
      }

        return JSONObject.quote("OK");
    }
/*

    public List<Talon> setAllActivity(
            @PathVariable(value = "patientId") String patientId,
            @PathVariable(value = "activity") Activity activity){
*/

}
