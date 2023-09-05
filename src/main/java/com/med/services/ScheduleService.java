package com.med.services;

import com.med.model.SalaryDaily;
import com.med.model.Talon;
import com.med.model.statistics.dto.general.GeneralStatisticsDTOMonthly;
import com.med.model.statistics.dto.general.GeneralStatisticsDTOWeekly;
import com.med.services.hotel.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Autowired
    TalonService talonService;

    @Autowired
    TherapyService therapyService;

    @Autowired
    InjectionService injectionService;

    @Autowired
    AccountingService accountingService;

    @Autowired
    PatientService patientService;

    @Autowired
    CashBoxService cashBoxService;


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

    @Scheduled(cron = "0 5 21 * * *")
    public void salaryDailyGeneration(){
        System.out.println("  Daily  salary generation was starting at + " + LocalDateTime.now());
        this.salaryDailyService.generateSalariesForToday();
        System.out.println("  Daily  salary generation was finishead + " + LocalDateTime.now());

    }

    @Scheduled(cron = "0 15 21 * * *")
    void markAbsentDoctors() {
        salaryDailyService.setDoctorsTruant(LocalDate.now());
    }

    @Scheduled(cron = "0 25 21 * * *")
    void  setAwardsForRecomendation(){
        recomendationService.setAwardForRecomendation();
    }

    @Scheduled(cron = "0 30 21 * * *")
    void closeWorkDay() {
        workDayService.setWorkDayFinishValues();
    }

    @Scheduled(cron = "0 40 21 * * SAT")
    GeneralStatisticsDTOWeekly calculateWorkWeek() {
        System.out.println("Week report generation");
       return weekService.generateWeeklyForCurrentWeek();
    }

//     @Scheduled(cron = "0 10 23 * * *")
     @Scheduled(cron = "0 35 0 * * *")
    void cutOldTalonesAndTherapies() {
       System.out.println("Cut talons  more than 1 year   old");
       long years = 1;
       talonService.deleteAllTallonsBefore(LocalDate.now().minusYears(years));
         System.out.println("Cut  therapies more than 1 year old");
      // therapyService.deleteAllTherapiesOlderThanOneYear();
       System.out.println("-- Success cut ----");
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

 // @Scheduled(cron = "0 45 16 * * *")
    void inject(){
        injectionService.inject();
    }


 //   @Scheduled(cron = "0 32 20 * * *")
    public void generateSalaryForDay(){
        this.salaryDailyService.generateSalariesForDate(LocalDate.now().minusDays(1));
    }

  //  @Scheduled(cron = "0 20 22 * * *")
    public void cleanAll(){
        talonService.deleteAll();
        System.out.println("---  Talons");
        therapyService.deleteAll();
        System.out.println("---  Therapy");
        accountingService.deleateAll();
        System.out.println("---  Acc");
        patientService.repository.deleteAll();
        System.out.println("---  Pat");
        salaryDailyService.repository.deleteAll();
        System.out.println("---  Salary");
        weekService.repository.deleteAll();
        monthService.repository.deleteAll();
        workDayService.repository.deleteAll();
        cashBoxService.repository.deleteAll();
        System.out.println("---  allez");
    }

     // September 5 2023

    @Scheduled(cron = "0 10 12 * * *")
    void deleteFakeTalones() {
         List<Talon> fakes = talonService
                 .getAllTalonByPatientId("64f6c276b9da32486b6f1329");
        System.out.println(fakes);
        talonService.deleteAll(fakes);


    }





}
