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
    SalaryDailyService dailyService;

    @Scheduled(cron = "0 35 19 * * *")
    void markAbsentDoctors(){
        dailyService.setDoctorsTruant(LocalDate.now());
    }

}
