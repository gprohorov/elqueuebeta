package com.med.controller;

import com.med.model.statistics.dto.accounting.CurrentReport;
import com.med.services.cashbox.impls.CashBoxServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


import com.med.model.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by george on 29.10.18.
 */
@RestController
@RequestMapping("/api/cashbox")
@CrossOrigin("*")
public class CashBoxController {

    @Autowired
    CashBoxServiceImpl service;

    @RequestMapping("/kassa")
    public int getKassa() {
        return service.getCashBox();
    }

    @RequestMapping("/tozero/{sum}")
    public Response setKassaToZero(@PathVariable(value = "sum")  int sum) {
        return service.toZero(sum);
    }

    @RequestMapping("/create")
    public Response createCash(@Valid @RequestBody CashBox cash) {
        return service.saveCash(cash);
    }

    @RequestMapping("/current/details")
    public CurrentReport getReportDetail() {
        return service.getCurrentReportDetails();
    }



}