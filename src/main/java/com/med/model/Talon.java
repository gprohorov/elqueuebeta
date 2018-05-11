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

    private int patientId;
    private LocalDate date;
    private Procedure procedure;
    private int zones;
    private String desc;
    private  LocalDateTime start;
    private LocalDateTime executionTime;
    private Doctor doctor;
    private int sum;



    public Talon() {
    }

    // full
    public Talon(ObjectId id, int patientId, LocalDate date, Procedure procedure, int zones, String desc, LocalDateTime executionTime, Doctor doctor, int sum) {
        this.id = id;
        this.patientId = patientId;
        this.date = date;
        this.procedure = procedure;
        this.zones = zones;
        this.desc = desc;
        this.executionTime = executionTime;
        this.doctor = doctor;
        this.sum = sum;
    }
    // without id
    public Talon(int patientId, LocalDate date, Procedure procedure, int zones, String desc, LocalDateTime executionTime, Doctor doctor, int sum) {
        this.patientId = patientId;
        this.date = date;
        this.procedure = procedure;
        this.zones = zones;
        this.desc = desc;
        this.executionTime = executionTime;
        this.doctor = doctor;
        this.sum = sum;
    }
    //  patient and procedure  for today
    public Talon(int patientId, Procedure procedure) {
        this.patientId = patientId;
        this.date = LocalDate.now();
        this.procedure = procedure;
        this.zones = 0;
        this.desc = "";
        this.executionTime = null;
        this.doctor = null;
        this.sum = 0;

    }

    public Talon(int patientId, LocalDate date, Procedure procedure) {
        this.patientId = patientId;
        this.date = date;
        this.procedure = procedure;
        this.zones = 0;
        this.desc = "";
        this.executionTime = null;
        this.doctor = null;
        this.sum = 0;

    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
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

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {this.sum = sum;}



    @Override
    public String toString() {
        return "Talon{" +
                "id=" + id +
                ", patientId=" + patientId +
                ", date=" + date +
                ", procedure=" + procedure +
                ", zones=" + zones +
                ", desc='" + desc + '\'' +
                ", executionTime=" + executionTime +
                ", doctor=" + doctor +
                ", sum=" + sum +

                '}';
    }
}
