package com.med.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by george on 3/9/18.
 */
public class Patient implements Comparable<Patient>{
   private int id;
   private Person person;
   private Therapy therapy;
   private List<Procedure> proceduresForToday = new ArrayList<>();
   private Status status;
   private LocalDateTime lastActivity;
   private long delta=0;
   private int balance;
   private Activity active;
   private Reckoning reckoning;


    public Patient() {
    }

    public Patient(int id, Person person, Therapy therapy, List<Procedure> proceduresForToday, Status status, LocalDateTime lastActivity, int balance, Activity active, Reckoning reckoning) {
        this.id = person.getId();
        this.person = person;
        this.therapy = therapy;
        this.proceduresForToday = proceduresForToday;
        this.status = status;
        this.lastActivity = lastActivity;
        this.balance = balance;
        this.active = active;
        this.reckoning = reckoning;
    }

    public Patient(Person person, Therapy therapy, List<Procedure> proceduresForToday, Status status, LocalDateTime lastActivity, int balance, Activity active, Reckoning reckoning) {
        this.id = person.getId();
        this.person = person;
        this.therapy = therapy;
        this.proceduresForToday = proceduresForToday;
        this.status = status;
        this.lastActivity = lastActivity;
        this.balance = balance;
        this.active = active;
        this.reckoning = reckoning;
    }

    public Patient(Person person, Therapy therapy, List<Procedure> proceduresForToday, Status status, LocalDateTime lastActivity, int balance, Activity active) {
        this.id = person.getId();
        this.person = person;
        this.therapy = therapy;
        this.proceduresForToday = proceduresForToday;
        this.status = status;
        this.lastActivity = lastActivity;
        this.balance = balance;
        this.active = active;
    }

    public Patient(Person person) {
        this.id = person.getId();
        this.person = person;
        this.active = Activity.NON_ACTIVE;
    }

    public long getDelta() {
        return delta;
    }

    public void setDelta(long delta) {
        this.delta = delta;
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

    public List<Procedure> getProceduresForToday() {
        return proceduresForToday;
    }

    public void setProceduresForToday(List<Procedure> proceduresForToday) {
        this.proceduresForToday = proceduresForToday;
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

    public Reckoning getReckoning() {
        return reckoning;
    }

    public void setReckoning(Reckoning reckoning) {
        this.reckoning = reckoning;
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

    @Override
    public int compareTo(Patient comparePatient) {

        int compareStatus = comparePatient.getStatus().getLevel();
        int thisStatus = this.getStatus().getLevel();
        int compareTime = comparePatient.getLastActivity().getSecond();

        if (compareStatus != this.getStatus().getLevel())
             {return  compareStatus -this.getStatus().getLevel();}
        else {
          //  System.out.println("the case");
            return this.getLastActivity().compareTo(comparePatient.getLastActivity());
        }
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", person=" + person +
                ", therapy=" + therapy +
                ", proceduresForToday=" + proceduresForToday +
                ", status=" + status +
                ", lastActivity=" + lastActivity +
                ", balance=" + balance +
                ", active=" + active +
                ", reckoning=" + reckoning +
                '}';
    }
}
