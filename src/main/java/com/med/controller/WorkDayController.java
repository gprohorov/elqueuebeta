package com.med.controller;


import com.med.model.workday.WorkDay;
import com.med.services.WorkDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by george on 27.02.19.
 */
@RestController
@RequestMapping("/api/workday")
@CrossOrigin("*")
public class WorkDayController {

    @Autowired
    WorkDayService service;


    @GetMapping("/get/{date}")
    public WorkDay showWorkDay(@PathVariable(value = "date") String date) {
        return service.getWorkDay(LocalDate.parse(date));
    }





}
