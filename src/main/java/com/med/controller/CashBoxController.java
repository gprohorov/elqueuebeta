package com.med.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.med.model.CashBox;
import com.med.model.Response;
import com.med.services.CashBoxService;

@RestController
@RequestMapping("/api/cashbox")
@CrossOrigin("*")
public class CashBoxController {

    @Autowired
    CashBoxService service;

    @RequestMapping("/kassa")
    public int getKassa() {
        return service.getCashBox();
    }

    @RequestMapping("/tozero/{sum}")
    public Response setKassaToZero(@PathVariable(value = "sum") int sum) {
        return service.toZero(sum);
    }

    @RequestMapping("/create")
    public Response createCash(@Valid @RequestBody CashBox cash) {
        return service.saveCash(cash);
    }
    
    @RequestMapping("/create-sa")
    public Response createCashSA(@Valid @RequestBody CashBox cash) {
    	return service.saveCashSA(cash);
    }
}