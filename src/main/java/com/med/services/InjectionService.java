package com.med.services;

import com.med.model.Card;
import com.med.model.Salary;
import com.med.model.SalaryType;
import com.med.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InjectionService {

    @Autowired
    SalaryService salaryService;
    @Autowired
    SalaryDailyService salaryDailyService;

    @PostConstruct
    void init() {
     //   salaryDailyGeneration(); 21/01/24
        if (LocalDate.now().equals(LocalDate.of(2024, Month.JANUARY, 28))) {
            this.salaryDailyGenerationForDays();
        }
    }


    public void salaryDailyGeneration(){
        System.out.println("  Daily  salary generation was starting at + " + LocalDateTime.now());
        this.salaryDailyService.generateSalariesForToday();
        System.out.println("  Daily  salary generation was finishead + " + LocalDateTime.now());

    }

    public void salaryDailyGenerationForDays(){
        List<LocalDate> days = new ArrayList<>(
                Arrays.asList(
                        LocalDate.of(2024, Month.JANUARY, 11),
                        LocalDate.of(2024, Month.JANUARY, 12),
                        LocalDate.of(2024, Month.JANUARY, 15)
                )
        );
        System.out.println("  Daily  salary re-calculation was started at + " + LocalDateTime.now());
        days.stream()
                .forEach(date -> this.salaryDailyService.generateSalariesForDate(date));

        System.out.println("  Daily  salary re-calculation was finished + " + LocalDateTime.now());

    }



    public void inject(){
        List<Salary> list = new ArrayList<>();
        Salary zahlivniak = new Salary(5,
                LocalDateTime.now().minusDays(3),
                SalaryType.AWARD,
                203574);
        Salary bor = new Salary(39,
                LocalDateTime.now().minusDays(3),
                SalaryType.AWARD,
                9784);
        Salary ali = new Salary(40,
                LocalDateTime.now().minusDays(3),
                SalaryType.AWARD,
                8291);
        Salary oni = new Salary(49,
                LocalDateTime.now().minusDays(3),
                SalaryType.PENALTY,
                7887);
        Salary kri = new Salary(48,
                LocalDateTime.now().minusDays(3),
                SalaryType.PENALTY,
                2642);



        list.add(zahlivniak);
        list.add(bor);
        list.add(ali);
        list.add(oni);
        list.add(kri);
     //   list.stream().forEach( item -> salaryService.createSalary(item));
        System.out.println(" INJECTED " + list.size() + " items");

    }
}
