package com.med.datastorage.injection;

import com.med.services.WorkMonthService;
import com.med.services.WorkWeekService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by george on 24.03.19.
 */
//@Service
public class InjectionMonthStat {

    @Autowired
    WorkMonthService monthService;

  @PostConstruct
    private void init() {
/*       for (int i = 1; i < 13; i++) {
           monthService.deleteReport(2019,i);
           monthService.createMonthlReportViaAccount(2019,i);
       }*/

       for (int i = 1; i < 3; i++) {
           monthService.deleteReport(2020,i);
           monthService.createMonthlReportViaAccount(2020,i);
       }
    }

}
