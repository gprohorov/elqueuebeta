package com.med.services;

import com.med.model.SalaryDaily;
import com.med.model.statistics.dto.general.GeneralStatisticsDTOMonthly;
import com.med.model.statistics.dto.general.GeneralStatisticsDTOWeekly;
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
    SalaryDailyService salaryDailyService;

    @Autowired
    RecomendationService recomendationService;

    @Autowired
    RecordService recordService;

    @Autowired
    WorkWeekService weekService;

    @Autowired
    WorkMonthService monthService;


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

    @Scheduled(cron = "0 5 19 * * *")
    public void salaryDailyGeneration(){
        this.salaryDailyService.generateSalariesForToday();
    }

    @Scheduled(cron = "0 15 19 * * *")
    void markAbsentDoctors() {
        salaryDailyService.setDoctorsTruant(LocalDate.now());
    }

    @Scheduled(cron = "0 25 19 * * *")
    void  setAwardsForRecomendation(){
        recomendationService.setAwardForRecomendation();
    }

    @Scheduled(cron = "0 30 19 * * *")
    void closeWorkDay() {
        workDayService.setWorkDayFinishValues();
    }

    @Scheduled(cron = "0 40 19 * * SAT")
    GeneralStatisticsDTOWeekly calculateWorkWeek() {
        System.out.println("Week report generation");
       return weekService.generateWeeklyForCurrentWeek();
    }
 // @Scheduled(cron = "1 13 21 * * *")
   @Scheduled(cron = "1 1 1 1 * *")
    GeneralStatisticsDTOMonthly calculateWorkMonth() {
        System.out.println("Month report   generation");
       return monthService.createRegularMonthReport();
    }

  // @Scheduled(cron = "0 55 19 * * *")
    GeneralStatisticsDTOMonthly calculateWorkMonth1() {
        System.out.println("Month report generation");
       return monthService.createMonthlReport(2019,10);
    }

   //   @Scheduled(cron = "0 10 22 * * *")
    void calculateWorkMonthes() {
        System.out.println("Month report generation");
        monthService.createMonthlReport(2019,10);
        System.out.println("Month report generation");
        monthService.createMonthlReport(2019,11);
        System.out.println("Month report generation");
        monthService.createMonthlReport(2019,12);
    }




  //  @Scheduled(cron = "0 40 19 * * *")





}
