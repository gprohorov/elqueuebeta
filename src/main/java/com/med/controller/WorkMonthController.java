package com.med.controller;

import com.med.model.statistics.dto.general.GeneralStatisticsDTOMonthly;
import com.med.model.statistics.dto.general.GeneralStatisticsDTOWeekly;
import com.med.services.WorkMonthService;
import com.med.services.WorkWeekService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workmonth")
@CrossOrigin("*")
public class WorkMonthController {

    @Autowired
    WorkMonthService service;

    @GetMapping("/get/list/{year}")
    public List<GeneralStatisticsDTOMonthly> showAllForYear(@PathVariable(value = "year") int year) {
        return service.getAllForYear(year);
    }




}