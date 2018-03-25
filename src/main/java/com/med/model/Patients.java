package com.med.model;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by george on 3/9/18.
 */
public class Patients {
    private List<Patient> folk;
    private LocalDate today;

    public Patients(List<Patient> folk, LocalDate today) {
        this.folk = folk;
        this.today = today;
    }

    public Patients() {
    }

    public List<Patient> getFolk() {
        return folk;
    }

    public void setFolk(List<Patient> folk) {
        this.folk = folk;
    }

    public LocalDate getToday() {
        return today;
    }

    public void setToday(LocalDate today) {
        this.today = today;
    }
}
