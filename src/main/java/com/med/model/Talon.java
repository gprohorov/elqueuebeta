package com.med.model;

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
    private String id;

    private String patientId;
    private LocalDate date;
    private Procedure procedure;
    private int zones=1;
    private String desc;
    private LocalDateTime start;
    private LocalDateTime executionTime;
    private Doctor doctor;
    private Status status = Status.SOCIAL;
    private int sum;
    /////////
    private Activity activity = Activity.NON_ACTIVE;


    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }



    public Talon() {
    }

    // full
    public Talon(String id, String patientId, LocalDate date, Procedure procedure, int zones, String desc, LocalDateTime executionTime, Doctor doctor, int sum) {
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
    public Talon(String patientId, LocalDate date, Procedure procedure, int zones, String desc, LocalDateTime executionTime, Doctor doctor, int sum) {
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
    public Talon(String patientId, Procedure procedure) {
        this.patientId = patientId;
        this.date = LocalDate.now();
        this.procedure = procedure;
        this.zones = 1;
        this.desc = "";
        this.executionTime = null;
        this.doctor = null;
        this.sum = 0;

    }

    public Talon(String patientId, Procedure procedure, LocalDate date) {
        this.patientId = patientId;
        this.date = date;
        this.procedure = procedure;
        this.zones = 1;
        this.desc = "";
        this.executionTime = null;
        this.doctor = null;
        this.sum = 0;

    }
    public Talon(String patientId, Procedure procedure, int days) {
        this.patientId = patientId;
        this.date = LocalDate.now().plusDays(days);
        this.procedure = procedure;
        this.zones = 1;
        this.desc = "";
        this.executionTime = null;
        this.doctor = null;
        this.sum = 0;

    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
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

    public int getProcedureId() {
        return this.procedure.getId();
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
                "id='" + id + '\'' +
                ", patientId='" + patientId + '\'' +
                ", date=" + date +
                ", procedure=" + procedure +
                ", zones=" + zones +
                ", desc='" + desc + '\'' +
                ", start=" + start +
                ", executionTime=" + executionTime +
                ", doctor=" + doctor +
                ", status=" + status +
                ", sum=" + sum +
                ", activity=" + activity +
                '}';
    }
}

