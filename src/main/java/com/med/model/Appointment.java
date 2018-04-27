package com.med.model;

import java.time.LocalDate;

/**
 * Created by george on 3/9/18.
 */


public class Appointment {
    private long id;
    private Patient patient;
    private LocalDate date;


    public Appointment(Patient patient, LocalDate date) {
        this.patient = patient;
        this.date = date;
    }

    public Appointment(Person person, LocalDate date) {
        this.patient = new Patient(person);
        this.date = date;
    }

    public Appointment(long id, Patient patient, LocalDate date) {
        this.id = id;
        this.patient = patient;
        this.date = date;
    }






    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
