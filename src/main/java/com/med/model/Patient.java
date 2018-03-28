package com.med.model;

import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * Created by george on 3/9/18.
 */
public class Patient {
   private int id;
   private Person person;
   private Therapy therapy;
   private HashMap<Procedure,Boolean> assignedProcedures; // for today
   private Status status;
   private LocalDateTime lastActivity;
   private int balance;
   private Activity active;

    public Patient() {
    }

    public Patient(int id, Person person,  Therapy therapy, HashMap<Procedure, Boolean> assignedProcedures, Status status, LocalDateTime lastActivity, int balance, Activity active) {
        this.id = person.getId();
        this.person = person;
        this.therapy = therapy;
        this.assignedProcedures = assignedProcedures;
        this.status = status;
        this.lastActivity = lastActivity;
        this.balance = balance;
        this.active = active;
    }


    public Patient(Person person) {
        this.id = person.getId();
        this.person = person;
        this.assignedProcedures= new HashMap<>();
        this.active = Activity.NON_ACTIVE;
    }

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

    public Therapy getTherapy() {
        return therapy;
    }

    public void setTherapy(Therapy therapy) {
        this.therapy = therapy;
    }

    public HashMap<Procedure, Boolean> getAssignedProcedures() {
        return assignedProcedures;
    }

    public void setAssignedProcedures(HashMap<Procedure, Boolean> assignedProcedures) {
        this.assignedProcedures = assignedProcedures;
    }


    public void assignProcedure(Procedure procedure){

        this.assignedProcedures.put(procedure,false);
    }

    public void disAssignProcedure(Procedure procedure){

        this.assignedProcedures.remove(procedure);
    }


        public void markProcedureAsExecuted(Procedure procedure){

        this.assignedProcedures.put(procedure,true);
    }


    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(LocalDateTime lastActivity) {
        this.lastActivity = lastActivity;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public Activity getActive() {
        return active;
    }

    public void setActive(Activity active) {
        this.active = active;
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
        return this.getPerson().getId();
    }



}
