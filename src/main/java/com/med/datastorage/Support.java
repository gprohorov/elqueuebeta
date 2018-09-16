package com.med.datastorage;

import com.med.services.hotel.record.impls.RecordServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

/**
 * Created by george on 13.09.18.
 */

@Configuration
public class Support {

    private LocalDate lastMonitoring;


    @Autowired
    RecordServiceImpl recordService;


    public void check() {
        System.out.println("-------------------------Checking was called");
        System.out.println(this.getLastMonitoring());
        if (this.getLastMonitoring() == null
                || this.getLastMonitoring().isBefore(LocalDate.now())) {
            this.run();
        }
    }

    private void run() {
        // Your tasks here
      //  recordService.recalculate();
        System.out.println("--------------Runing");

        this.setLastMonitoring(LocalDate.now());
    }

    public LocalDate getLastMonitoring() {
        return lastMonitoring;
    }

    public void setLastMonitoring(LocalDate lastMonitoring) {
        this.lastMonitoring = lastMonitoring;
    }
}
