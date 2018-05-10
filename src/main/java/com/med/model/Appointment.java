package com.med.model;

import java.time.LocalDate;

/**
 * Created by george on 3/9/18.
 */
public class Appointment {

    private Patient patient;
    private LocalDate date;

    public Appointment() {
    }

    public Appointment(Patient patient, LocalDate date) {
        this.patient = patient;
        this.date = date;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
