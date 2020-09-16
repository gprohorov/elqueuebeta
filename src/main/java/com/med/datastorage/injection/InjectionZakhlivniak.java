package com.med.datastorage.injection;

import com.med.model.Doctor;
import com.med.model.SalaryDaily;
import com.med.repository.SalaryDailyRepository;
import com.med.services.DoctorService;
import com.med.services.SalaryDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by george on 24.03.19.
 */
//@Service
public class InjectionZakhlivniak {

    @Autowired
    SalaryDailyRepository salaryDailyRepository;


    //@PostConstruct
    void init() {
        List<SalaryDaily> list = salaryDailyRepository.findAll().stream()
                .filter(item-> item.getDate().isAfter(LocalDate.now().minusDays(22)))
                .filter(item->item.getDoctorId()==5)
                .collect(Collectors.toList());
        list.stream().forEach(item -> {
            item.setStavka(0);
            System.out.println(item.getDate());
        });
        salaryDailyRepository.saveAll(list);
    }


}
