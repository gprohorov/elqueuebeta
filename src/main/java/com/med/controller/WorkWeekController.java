package com.med.controller;

import com.med.model.statistics.dto.general.GeneralStatisticsDTOWeekly;
import com.med.model.workday.WorkDay;
import com.med.services.WorkDayService;
import com.med.services.WorkWeekService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/workweek")
@CrossOrigin("*")
public class WorkWeekController {

    @Autowired
    WorkWeekService service;

    @GetMapping("/get/list/{year}")
    public List<GeneralStatisticsDTOWeekly> showAllForYear(@PathVariable(value = "year") int year) {
        return service.getAllForYear(year);
    }




}