package com.med.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by george on 22.04.18.
 */
@Document(collection = "patient")
public class Talon {

    @Id
    private long id;

    private LocalDate date;
    private int patientId;
    private Procedure procedure;
    private int zones;
    private String desc;
    private LocalDateTime doneTime;
    private Doctor doctor;

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Talon() {
    }

    public Talon(long id, LocalDate date, int patient_id, Procedure procedure, int zones, String desc, LocalDateTime doneTime) {
        this.id = id;
        this.date = date;
        this.patientId = patient_id;
        this.procedure = procedure;
        this.zones = zones;
        this.desc = desc;
        this.doneTime = doneTime;
    }

    public Talon(int patient_id, Procedure procedure, int zones, String desc) {
        this.id = 0;
        this.date = LocalDate.now();
        this.patientId = patient_id;
        this.procedure = procedure;
        this.zones = zones;
        this.desc = desc;
        this.doneTime = null;
        this.doctor = null;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patient_id) {
        this.patientId = patient_id;
    }

    public Procedure getProcedure() {
        return procedure;
    }

    public void setProcedure(Procedure procedure) {
        this.procedure = procedure;
    }

    public int getZones() {
        return zones;
    }

    public void setZones(int zones) {
        this.zones = zones;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public LocalDateTime getDoneTime() {
        return doneTime;
    }

    public void setDoneTime(LocalDateTime doneTime) {
        this.doneTime = doneTime;
    }
}
