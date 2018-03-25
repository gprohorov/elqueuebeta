package com.med.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by george on 3/9/18.
 */
public class Patient {
   private int id;
   private Person person;
   private LocalDate date;
   private Therapy therapy;
   private Map<Procedure,Boolean> assignedProcedures = new HashMap<>(); // for today
   private Status status;
   private LocalDateTime lastActivity;
   private int balance;
   private Activity active;

    public Patient() {
    }

    public Patient(int id, Person person, LocalDate date, Therapy therapy, Map<Procedure, Boolean> assignedProcedures, Status status, LocalDateTime lastActivity, int balance, Activity active) {
        this.id = id;
        this.person = person;
        this.date = date;
        this.therapy = therapy;
        this.assignedProcedures = assignedProcedures;
        this.status = status;
        this.lastActivity = lastActivity;
        this.balance = balance;
        this.active = active;
    }




    public Patient(Person person, LocalDate date) {
        this.id = person.getId();
        this.person = person;
        this.date = date;
        this.assignedProcedures= Collections.emptyMap();
        this.active = Activity.NON_ACTIVE;
    }

    public Patient(Person person) {
        this.id = person.getId();
        this.person = person;
        this.active = Activity.NON_ACTIVE;
    }

    public Therapy getTherapy() {
        return therapy;
    }

    public void setTherapy(Therapy therapy) {
        this.therapy = therapy;
    }

    public LocalDateTime getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(LocalDateTime lastActivity) {
        this.lastActivity = lastActivity;
    }

    public Activity getActive() {return active;}

    public void setActive(Activity active) {this.active = active;}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Therapy getDiagnosis() {
        return therapy;
    }

    public void setDiagnosis(Therapy therapy) {
        this.therapy = therapy;
    }



    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Patient patient = (Patient) o;

        return this.getPerson().getId() == (patient.getPerson().getId());
    }

    @Override
    public int hashCode() {
        return getPerson().hashCode();
    }



}
