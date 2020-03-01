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
    private void init() {


/*

       for (int i = 32; i < 52; i++) {
           weekService.createWeeklyViaAccounting(i,2018);
       }
       for (int i = 0; i < 52; i++) {
           weekService.createWeeklyViaAccounting(i,2019);
       }
*/
     weekService.drop();

       for (int i = 32; i < 52; i++) {
           weekService.createWeekly(2019, i);
           System.out.println("" + "create week report " + i);
       }




       for (int i = 0; i < 9; i++) {
         System.out.println("" + "Try to creat week report " + i);
           weekService.createWeekly(2020, i);
           System.out.println("" + "Has been created week report " + i);
       }


    }
}
