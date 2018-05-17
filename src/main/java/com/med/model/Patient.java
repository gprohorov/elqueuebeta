package com.med.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by george on 3/9/18.
 */
@Document(collection = "patient")
public class Patient implements Comparable<Patient> {

    @Id
    private String id;

    private Person person;
    @Transient
    private Therapy therapy;
    @Transient
    private List<Talon> talons = new ArrayList<>();
    private LocalDateTime lastActivity;
    private LocalDateTime startActivity;
    private Status status = Status.SOCIAL;



    public Patient() {
    }

    public Patient(String id, Person person, Therapy therapy, List<Talon> talons, LocalDateTime lastActivity, LocalDateTime startActivity, Status status) {
        this.id = id;
        this.person = person;
        this.therapy = therapy;
        this.talons = talons;
        this.lastActivity = lastActivity;
        this.startActivity = startActivity;
        this.status = status;
    }

    public Patient(Person person, Therapy therapy, List<Talon> talons, LocalDateTime lastActivity, LocalDateTime startActivity, Status status) {
        this.person = person;
        this.therapy = therapy;
        this.talons = talons;
        this.lastActivity = lastActivity;
        this.startActivity = startActivity;
        this.status = status;
    }

    public Patient(Person person) {
        this.talons = new ArrayList<>();
        this.person = person;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public List<Talon> getTalons() {
        return talons;
    }

    public void setTalons(List<Talon> talons) {
        this.talons = talons;
    }

    public LocalDateTime getStartActivity() {
        return startActivity;
    }

    public void setStartActivity(LocalDateTime startActivity) {
        this.startActivity = startActivity;
    }

    public LocalDateTime getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(LocalDateTime lastActivity) {
        this.lastActivity = lastActivity;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getDelta(){
        Long delta =null;
           if (this.getLastActivity() != null) {
               delta = ChronoUnit.MINUTES.between(this.getLastActivity()
                     , LocalDateTime.now());
           }
        return delta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Patient)) return false;

        Patient patient = (Patient) o;

        return getId().equals(patient.getId());
    }

    @Override
    public int hashCode() {
        return this.getId().hashCode();
    }



    @Override
    public int compareTo(Patient comparePatient) {

        int compareStatus = comparePatient.getStatus().getLevel();

        if (compareStatus != this.getStatus().getLevel()) {
            return comparePatient
                    .getStatus().getLevel() - this.getStatus().getLevel();
        } else {
              if(this.getLastActivity()!= null && comparePatient.getLastActivity()!=null) {
                  return this.getLastActivity()
                          .compareTo(comparePatient.getLastActivity());
              } else return 0;
        }
    }

}
