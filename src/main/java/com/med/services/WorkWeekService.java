package com.med.services;


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

    GeneralStatisticsDTOWeekly getWeekly(String id){
        return repository.findById(id).get();
    }

    GeneralStatisticsDTOWeekly setWeekly(GeneralStatisticsDTOWeekly weekly){
        return repository.save(weekly);
    }


    public List<GeneralStatisticsDTOWeekly> getAllForYear(int year){
        return repository.findAll().stream()
                .filter(week -> week.getYear()==year)
                .collect(Collectors.toList());
    }

    GeneralStatisticsDTOWeekly createWeekly(int year, int week){

        LocalDate theFirstSatOfYear = LocalDate.of(2019,Month.JANUARY, 5 );
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
        weekly.setYear(LocalDate.now().getYear());
        String weekString = from.plusDays(1).getDayOfMonth() + ""
                + from.plusDays(1).getMonth().toString().substring(0,3).toLowerCase()
                + " - "
                + to.minusDays(1).getDayOfMonth() + ""
                + from.plusDays(1).getMonth().toString().substring(0,3).toLowerCase();

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


        return createWeekly(currentYear,lastWeek+1);
    }

   // @Scheduled(cron = "0 26 21 * * *")
    public void generateWeekReport(){

        System.out.println("----week------");
        repository.deleteAll();

        for (int i = 9; i <= 36 ; i++) {
          //  this.createWeekly(i);

            System.out.println(createWeekly(2019, i).toString());
        }

       //

    }


}
