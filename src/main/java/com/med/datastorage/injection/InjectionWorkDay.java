package com.med.datastorage.injection;

import com.med.services.WorkDayService;
import com.med.services.WorkMonthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

/**
 * Created by george on 24.03.19.
 */
@Service
public class InjectionWorkDay {

    @Autowired
    WorkDayService service;

 //  @PostConstruct
    private void init() {

        LocalDate start = LocalDate.of(2020, 3, 27);

       for (int i = 0; i <= 90 ; i++) {

           service.setWorkDayFinishValues(start.minusDays(i));
       }


    }


}
