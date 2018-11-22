package com.med.controller;

import com.med.model.statistics.dto.accounting.CurrentReport;
import com.med.services.cashbox.impls.CashBoxServiceImpl;
import com.med.services.dayoutlay.OutlayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


import com.med.model.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
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
    @Autowired
    OutlayService dayOutlayService;
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

    //------------------------ 18 nov
    @RequestMapping("/current/details")
    public CurrentReport getReportDetail() {
        return service.getCurrentReportDetails();
    }

    //-------------------------------- 22 nov

    @RequestMapping("/outlay/total//{from}/{to}")
    public Outlay getOutlay(
            @PathVariable(value = "from") String from,
            @PathVariable(value = "to") String to )
    {
        return  dayOutlayService.getOutlay(LocalDate.parse(from), LocalDate.parse(to));
    }

    @RequestMapping("/outlay/details/salary/{from}/{to}")
    public List<CashBox> getOutlaySalary(
            @PathVariable(value = "from") String from,
            @PathVariable(value = "to") String to )
    {
        return  dayOutlayService.getOutlaySalary(LocalDate.parse(from), LocalDate.parse(to));
    }

    @RequestMapping("/outlay/details/machine/{from}/{to}")
    public List<CashBox> getOutlayMachine(
            @PathVariable(value = "from") String from,
            @PathVariable(value = "to") String to )
    {
        return  dayOutlayService.getOutlayMachine(LocalDate.parse(from), LocalDate.parse(to));
    }



}