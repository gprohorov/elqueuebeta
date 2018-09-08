package com.med.model;

import java.time.LocalDate;

/**
 * Created by george on 16.08.18.
 */
public class LastTalonInfo {
    private LocalDate date;
    private int zones;
    private Doctor doctor;

    public LastTalonInfo() { }

    public LastTalonInfo(LocalDate date, int zones, Doctor doctor) {
        this.date = date;
        this.zones = zones;
        this.doctor = doctor;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getZones() {
        return zones;
    }

    public void setZones(int zones) {
        this.zones = zones;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}
