package com.med.datastorage.injection;

import com.med.model.Category;
import com.med.model.hotel.Chamber;
import com.med.model.hotel.Koika;
import com.med.repository.hotel.ChamberRepository;
import com.med.repository.hotel.KoikaRepository;
import com.med.services.WorkWeekService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by george on 24.03.19.
 */
@Service
public class InjectionWeekStat {

    @Autowired
    WorkWeekService weekService;




   @PostConstruct
    void init() {
       weekService.drop();


       for (int i = 0; i < 52; i++) {
           weekService.createWeeklyViaAccounting(i,2019);
       }


       for (int i = 0; i < 9; i++) {
           weekService.createWeeklyViaAccounting(i,2020);
       }


    }
}
