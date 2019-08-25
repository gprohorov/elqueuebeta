package com.med.services;

import com.med.model.balance.Accounting;
import com.med.services.hotel.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by george on 24.03.19.
 */
@Service
public class ScheduleService {

    @Autowired
    WorkDayService workDayService;

    @Autowired
    SalaryDailyService dailyService;

    @Autowired
    RecomendationService recomendationService;

    @Autowired
    RecordService recordService;

    @Scheduled(cron = "0 0 7 * * *")
    void initWorkDay() {
       workDayService.initWorkDay();
    }

    @Scheduled(cron = "0 0 10 * * *")
    void setWorkDayStart() {
        workDayService.setWorkDayStart();
    }

    @Scheduled(cron = "0 0 12 * * *")
    void hotelBillsForToday(){ recordService.generateBillsForAllLodgers();
    }

    @Scheduled(cron = "0 15 19 * * *")
    void markAbsentDoctors() {
        dailyService.setDoctorsTruant(LocalDate.now());
    }

    @Scheduled(cron = "0 25 19 * * *")
    void  setAwardsForRecomendation(){
        recomendationService.setAwardForRecomendation();
    }

    @Scheduled(cron = "0 30 19 * * *")
    void closeWorkDay() {
        workDayService.setWorkDayFinishValues();
    }


  //  @Scheduled(cron = "0 40 19 * * *")





}