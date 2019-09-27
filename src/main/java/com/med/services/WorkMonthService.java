package com.med.services;

import com.med.model.statistics.dto.general.GeneralStatisticsDTOMonthly;
import com.med.model.workday.WorkDay;
import com.med.repository.WorkMonthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
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
          int month = LocalDate.now().getMonthValue();
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


          LocalDate to = LocalDate.now();
          LocalDate from = LocalDate.of(year, to.getMonthValue() -1, 1);


          if (to.getMonth().equals(Month.JANUARY)){

               from = LocalDate.of(year-1, 12, 1);
          }




          int outcome = cashBoxService.getOutlayForPeriod(from.minusDays(1), to);
          monthly.setOutcome(outcome);

          System.out.println("Month generation for "+ year + "/" + month + " complete");

          return repository.save(monthly);
     }

   //Hope1234  @Scheduled(cron = "0 0 18 * * *")
       private void createMonthly(){
            for (int i = 3; i < 9; i++) {

                 this.createMonthlReport(2019, i);

            }
       }
  //



}
