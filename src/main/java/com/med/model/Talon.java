package com.med.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by george on 22.04.18.
 */
@Document
public class Talon {

    @Id
    private ObjectId id;

    private LocalDate date;
    private Procedure procedure;
    private int zones;
    private String desc;
    private LocalDateTime executionTime;
    private Doctor doctor;


    public Talon() {
    }

    public Talon(ObjectId id, LocalDate date, Procedure procedure, int zones, String desc, LocalDateTime executionTime, Doctor doctor) {
        this.id = id;
        this.date = date;
        this.procedure = procedure;
        this.zones = zones;
        this.desc = desc;
        this.executionTime = executionTime;
        this.doctor = doctor;
    }

    public Talon(LocalDate date, Procedure procedure, int zones, String desc, LocalDateTime executionTime, Doctor doctor) {
        this.date = date;
        this.procedure = procedure;
        this.zones = zones;
        this.desc = desc;
        this.executionTime = executionTime;
        this.doctor = doctor;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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

    public LocalDateTime getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(LocalDateTime executionTime) {
        this.executionTime = executionTime;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}
