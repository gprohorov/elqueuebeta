package com.med.services;


import com.med.model.balance.Accounting;
import com.med.model.balance.PaymentType;
import com.med.model.statistics.dto.general.GeneralStatisticsDTOWeekly;
import com.med.model.workday.WorkDay;
import com.med.repository.WorkWeekRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkWeekService {

    @Autowired
    WorkWeekRepository repository;

    @Autowired
    WorkDayService workDayService;

    @Autowired
    CashBoxService cashBoxService;

    @Autowired
    AccountingService accountingService;

    GeneralStatisticsDTOWeekly getWeekly(String id){
        return repository.findById(id).get();
    }

    GeneralStatisticsDTOWeekly setWeekly(GeneralStatisticsDTOWeekly weekly){
        return repository.save(weekly);
    }

    public void drop(){
        repository.deleteAll();
    }



    public List<GeneralStatisticsDTOWeekly> getAllForYear(int year){
        return repository.findAll().stream()
                .filter(week -> week.getYear()==year)
                .collect(Collectors.toList());
    }

    public GeneralStatisticsDTOWeekly createWeekly(int year, int week){

        LocalDate theFirstSatOfYear = LocalDate.of(year,Month.JANUARY, 5 );
        LocalDate to = theFirstSatOfYear.plusDays( 7 * week + 1);
        LocalDate from = to.minusDays(8);
        List<WorkDay> workDays = workDayService.getAll().stream()
                .filter(workDay -> workDay.getDate().isAfter(from))
                .filter(workDay -> workDay.getDate().isBefore(to))
                .collect(Collectors.toList());

   //    workDays.stream().map(WorkDay::getDate).distinct()
   //            .forEach(System.out::println);

        GeneralStatisticsDTOWeekly weekly = new GeneralStatisticsDTOWeekly();

        weekly.setWeekNumber(week);
        weekly.setYear(year);
        String weekString = from.plusDays(1).getDayOfMonth() + ""
                + from.plusDays(1).getMonth().toString().substring(0,3).toLowerCase()
                + " - "
                + to.minusDays(1).getDayOfMonth() + ""
                + to.plusDays(1).getMonth().toString().substring(0,3).toLowerCase();

        weekly.setWeek(weekString);

        int patients = workDays.stream()
                .mapToInt(WorkDay::getActivePatients).sum();
        weekly.setPatients(patients);

        int bill = workDays.stream()
                .mapToInt(WorkDay::getSumForExecutedProcedures).sum();
        weekly.setBill(bill);

        int cash = workDays.stream()
                .mapToInt(WorkDay::getCash).sum();
        weekly.setCash(cash);

       int card = workDays.stream()
                .mapToInt(WorkDay::getCard).sum();
        weekly.setCard(card);

       int discount = workDays.stream()
                .mapToInt(WorkDay::getDiscount).sum();
        weekly.setDiscount(discount);

       int outcome = cashBoxService.getOutlayForPeriod(from, to);
        weekly.setOutcome(outcome);

        System.out.println("Week generation complete");
        return this.repository.save(weekly);
    }

    public GeneralStatisticsDTOWeekly generateWeeklyForCurrentWeek(){
        int currentYear = LocalDate.now().getYear();
        int lastWeek = this.getAllForYear(currentYear).stream()
                .mapToInt(GeneralStatisticsDTOWeekly::getWeekNumber)
                .max().orElse(0);
        System.out.println("Current week gemeration ");

        return createWeekly(currentYear,lastWeek+1);
    }


    // 27 sep
    public GeneralStatisticsDTOWeekly createWeeklyViaAccounting(int week, int yr) {

        LocalDate firstSunday = LocalDate.of(yr-1, Month.DECEMBER, 31);

        if (yr ==2019) {
             firstSunday = LocalDate.of(2018, Month.DECEMBER, 30);
        }
        int weekNumber = week;
        LocalDate from = firstSunday.plusDays(7*weekNumber);
       System.out.println(from.toString());
        LocalDate to = from.plusDays(7);

      //  System.out.println(from.toString() + " - " + to.toString());

        List<Accounting> accountings =
                accountingService.getByDateBetween(from.minusDays(1), to.plusDays(1));
      //  accountings.stream().map(Accounting::getDate).sorted()
        //        .forEach(System.out::println);

        GeneralStatisticsDTOWeekly weekly = new GeneralStatisticsDTOWeekly();

        weekly.setWeekNumber(weekNumber);
        weekly.setYear(yr);
        String weekString = from.plusDays(0).getDayOfMonth() + ""
                + from.plusDays(1).getMonth().toString().substring(0,3).toLowerCase()
                + " - "
                + to.minusDays(1).getDayOfMonth() + ""
                + to.plusDays(1).getMonth().toString().substring(0,3).toLowerCase();

        weekly.setWeek(weekString);

        int patients = (int) accountings.stream().filter(acc->acc.getSum() < 0)
                .map(accounting -> (accounting.getPatientId()
                        + accounting.getDate().toString()) )
                .distinct().count();
        weekly.setPatients(patients);

        int bill = (int) -1 * accountings.stream().filter(acc->acc.getSum() < 0)
                .mapToInt(Accounting::getSum).sum();
        weekly.setBill(bill);

        int cash = accountings.stream().filter(acc->acc.getPayment().equals(PaymentType.CASH))
                .mapToInt(Accounting::getSum).sum();
        weekly.setCash(cash);

        int card = accountings.stream().filter(acc->acc.getPayment().equals(PaymentType.CARD))
                .mapToInt(Accounting::getSum).sum();
        weekly.setCard(card);

        int discount = accountings.stream().filter(acc->acc.getPayment().equals(PaymentType.DISCOUNT))
                .mapToInt(Accounting::getSum).sum();
        weekly.setDiscount(discount);

        int outcome = cashBoxService.getOutlayForPeriod(from.minusDays(1), to);
        weekly.setOutcome(outcome);

        System.out.println("Week generation complete");
       //
       // System.out.println(weekly.toString());

        return this.repository.save(weekly);

    }

    //-----------------   injections
   //  @Scheduled(cron = "0 17 17 * * *")
    public void generateWeekReport(){


        System.out.println("----week------");
        repository.deleteAll();

        int year = 2018;
        for (int i = 36; i <= 52 ; i++) {

            createWeeklyViaAccounting(year,i);

            System.out.println( year + " " + i);
        }

         year = 2019;
        for (int i = 0 ; i <= 39 ; i++) {

            createWeeklyViaAccounting(year,i);

            System.out.println( year + " " + i);
        }


    }

    //@Scheduled(cron = "0 7 21 * * *")
    public void generateWeekViaAccount(){

        repository.deleteAll();

        for (int i = 34; i < 52 ; i++) {
           this.createWeeklyViaAccounting(i,2018);
        }

        for (int i = 0; i < 39 ; i++) {
            this.createWeeklyViaAccounting(i,2019);
        }

        }




}
