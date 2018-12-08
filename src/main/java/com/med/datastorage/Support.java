package com.med.datastorage;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.med.services.hotel.RecordService;

@Configuration
public class Support {

    private LocalDate lastMonitoring;

    @Autowired
    RecordService recordService;

    public void check() {
        System.out.println("----------------- Checking was called -----------------");
        System.out.println(this.getLastMonitoring());
        if (this.getLastMonitoring() == null || this.getLastMonitoring().isBefore(LocalDate.now())) {
            this.run();
        }
    }

    private void run() {
        // Your tasks here
        recordService.recalculate();
        this.setLastMonitoring(LocalDate.now());
    }

    public LocalDate getLastMonitoring() {
        return this.lastMonitoring;
    }

    public void setLastMonitoring(LocalDate lastMonitoring) {
        this.lastMonitoring = lastMonitoring;
    }
}