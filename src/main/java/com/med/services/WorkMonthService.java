package com.med.services;

import com.med.model.balance.Accounting;
import com.med.model.balance.PaymentType;
import com.med.model.statistics.dto.general.GeneralStatisticsDTOMonthly;
import com.med.model.workday.WorkDay;
import com.med.repository.WorkMonthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkMonthService {

     @Autowired
     WorkMonthRepository repository;

     @Autowired
     WorkDayService workDayService;

     @Autowired
     CashBoxService cashBoxService;

     @Autowired
     AccountingService accountingService;

     public GeneralStatisticsDTOMonthly get(String id){

          return repository.findById(id).orElse(null);
     }


     public GeneralStatisticsDTOMonthly create(GeneralStatisticsDTOMonthly item){
          return repository.save(item);
     }


     public List<GeneralStatisticsDTOMonthly> getAll(){
          return repository.findAll();
     }

     public void deleteAll(){ repository.deleteAll(); }

     public  List<GeneralStatisticsDTOMonthly> getAllForYear(int year){

          return this.getAll().stream()
                  .filter(month->month.getYear() == year)
                  .collect(Collectors.toList());
     }


     // must created on the 1st of each month at 01.00 AM
     public GeneralStatisticsDTOMonthly createRegularMonthReport(){

          int year = LocalDate.now().getYear();
          int month = LocalDate.now().getMonthValue() -1 ;
          if(LocalDate.now().getMonth().equals(Month.JANUARY)){
               year-=1;
               month=12;
          }

          System.out.println("Regular month generation " + year +"/" + month);
          return this.createMonthlReport(year,month);
     }




     public GeneralStatisticsDTOMonthly createMonthlReport(int yr, int mnth){

           final int year = yr;
           final int month = mnth;

          List<WorkDay> workDays = workDayService.getAll().stream()
                  .filter(day->day.getDate().getYear() == year)
                  .filter(day->day.getDate().getMonthValue() ==month )
                  .collect(Collectors.toList());
          
          GeneralStatisticsDTOMonthly monthly = new GeneralStatisticsDTOMonthly();
          
          monthly.setYear(year);
          monthly.setMonth(workDays.get(0).getDate().getMonth().name().substring(0,3));
          monthly.setMonthNumber(month);


          int patients = workDays.stream()
                  .mapToInt(WorkDay::getActivePatients).sum();
          monthly.setPatients(patients);

          int bill = workDays.stream()
                  .mapToInt(WorkDay::getSumForExecutedProcedures).sum();
          monthly.setBill(bill);

          int cash = workDays.stream()
                  .mapToInt(WorkDay::getCash).sum();
          monthly.setCash(cash);

          int card = workDays.stream()
                  .mapToInt(WorkDay::getCard).sum();
          monthly.setCard(card);

          int discount = workDays.stream()
                  .mapToInt(WorkDay::getDiscount).sum();
          monthly.setDiscount(discount);

          int outcome = cashBoxService.getOutlayForTheMonth(yr,mnth);
          monthly.setOutcome(outcome);

          System.out.println("Month generation for "+ year + "/" + month + " complete");

          return repository.save(monthly);
     }


     //28 sep 2019
     public GeneralStatisticsDTOMonthly createMonthlReportViaAccount(int yr, int mnth){

          final int year = yr;
          final int month = mnth;

          List<WorkDay> workDays = workDayService.getAll().stream()
                  .filter(day->day.getDate().getYear() == year)
                  .filter(day->day.getDate().getMonthValue() ==month )
                  .collect(Collectors.toList());

          List<Accounting> accountings =
                  accountingService.getAll().stream()
                  .filter(accounting -> accounting.getDate().getYear() == year)
                  .filter(accounting -> accounting.getDate().getMonthValue() == month)
                  .collect(Collectors.toList());

          GeneralStatisticsDTOMonthly monthly = new GeneralStatisticsDTOMonthly();

          monthly.setYear(year);
          monthly.setMonth(Month.of(mnth).toString());
          monthly.setMonthNumber(month);

          int patients = (int) accountings.stream()
                  .map(accounting -> (accounting.getPatientId() + accounting.getDate()))
                  .distinct().count();
          monthly.setPatients(patients);

          int bill = (int) -1 * accountings.stream().filter(acc->acc.getSum() < 0)
                  .mapToInt(Accounting::getSum).sum();
          monthly.setBill(bill);

          int cash = accountings.stream().filter(acc->acc.getPayment().equals(PaymentType.CASH))
                  .mapToInt(Accounting::getSum).sum();
          monthly.setCash(cash);

          int card = accountings.stream().filter(acc->acc.getPayment().equals(PaymentType.CARD))
                  .mapToInt(Accounting::getSum).sum();
          monthly.setCard(card);

          int discount = accountings.stream().filter(acc->acc.getPayment().equals(PaymentType.DISCOUNT))
                  .mapToInt(Accounting::getSum).sum();
          monthly.setDiscount(discount);

          int outcome = cashBoxService.getOutlayForMonth(month, year);
          monthly.setOutcome(outcome);

          System.out.println("Month generation " + monthly.getMonth() + " complete");

          return repository.save(monthly);
     }





     //------------------------------------ INJECTION
   //  @Scheduled(cron = "0 30 19 * * *")
       private void createMonthlyViaAcc(){
          repository.deleteAll();

          int yr = 2018;
            for (int i = 9; i <= 12; i++) {
                 System.out.println(Month.of(i).toString() + " " + yr);
                 this.createMonthlReportViaAccount(yr,i);
            }
           yr = 2019;
          for (int i = 1; i <= 9; i++) {
               System.out.println(Month.of(i).toString() + " " + yr);
               this.createMonthlReportViaAccount(yr,i);
          }

     }

     public void deleteReport(int year, int month) {
          GeneralStatisticsDTOMonthly report = this.getAllForYear(year).stream()
                  .filter(rep -> rep.getMonthNumber() == month)
                  .findFirst().orElse(null);
          if(report != null) {
               repository.delete(report);
          }else System.out.println(" " + year + "/" + month + " not found");

     }
     //



}
