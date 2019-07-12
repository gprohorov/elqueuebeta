package com.med.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.med.model.workday.WorkDay;
import com.med.services.WorkDayService;

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