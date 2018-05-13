package com.med.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by george on 3/9/18.
 */
@Document(collection = "patient")
public class Patient {

    @Id
    private ObjectId id;

    private Person person;
    @Transient
    private Therapy therapy;
    @Transient
    private List<Talon> talons = new ArrayList<>();
    private LocalDateTime lastActivity;
    private LocalDateTime startActivity;
    //   private int balance;
    // private Reckoning reckoning;

    /////////   need to create dao method -  find by last name


    public Patient() {
    }

    public Patient(ObjectId id, Person person, Therapy therapy, List<Talon> talons, LocalDateTime lastActivity, LocalDateTime startActivity) {
        this.id = id;
        this.person = person;
        this.therapy = therapy;
        this.talons = talons;
        this.lastActivity = lastActivity;
        this.startActivity = startActivity;
    }

    public Patient(Person person, Therapy therapy, List<Talon> talons, LocalDateTime lastActivity, LocalDateTime startActivity) {
        this.person = person;
        this.therapy = therapy;
        this.talons = talons;
        this.lastActivity = lastActivity;
        this.startActivity = startActivity;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
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

    /*
    @Override
    public int hashCode() {
       return this.getId();
    }

    @Override
    public int compareTo(Patient comparePatient) {

        int compareStatus = comparePatient.getStatus().getLevel();
        int thisStatus = this.getStatus().getLevel();
        int compareTime = comparePatient.getLastActivity().getSecond();

        if (compareStatus != this.getStatus().getLevel()) {
            return compareStatus - this.getStatus().getLevel();
        } else {
            //  System.out.println("the case");
            return this.getLastActivity().compareTo(comparePatient.getLastActivity());
        }
    }
    */

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
}
