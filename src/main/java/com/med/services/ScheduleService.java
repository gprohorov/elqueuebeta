package com.med.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * Created by george on 24.03.19.
 */
@Service
public class ScheduleService {

    @Autowired
    WorkDayService workDayService;

    @Autowired
    SalaryDailyService dailyService;

    @Scheduled(cron = "0 0 7 * * *")
    void initWorkDay() {
       workDayService.initWorkDay();
    }

    @Scheduled(cron = "0 0 10 * * *")
    void setWorkDayStart() {
        workDayService.setWorkDayStart();
    }

    @Scheduled(cron = "0 28 19 * * *")
    void markAbsentDoctors() {
        dailyService.setDoctorsTruant(LocalDate.now());
        System.out.println("Truant marked");
    }

    @Scheduled(cron = "0 32 19 * * *")
    void closeWorkDay() {
        workDayService.setWorkDayFinishValues();
    }


}