package com.med.model;

import java.time.LocalDateTime;

/**
 * Created by george on 3/20/18.
 */
public class Assignment {
    private int id;
    private LocalDateTime assignTime;
    private Patient patient;
    private Procedure procedure;
    private Doctor executor;
    private LocalDateTime executeTime;


    public Assignment(int id, LocalDateTime assignTime, Patient patient, Procedure procedure, Doctor executor, LocalDateTime executeTime) {
        this.id = id;
        this.assignTime = assignTime;
        this.patient = patient;
        this.procedure = procedure;
        this.executor = executor;
        this.executeTime = executeTime;
    }

    public Assignment() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getAssignTime() {
        return assignTime;
    }

    public void setAssignTime(LocalDateTime assignTime) {
        this.assignTime = assignTime;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Procedure getProcedure() {
        return procedure;
    }

    public void setProcedure(Procedure procedure) {
        this.procedure = procedure;
    }

    public Doctor getExecutor() {
        return executor;
    }

    public void setExecutor(Doctor executor) {
        this.executor = executor;
    }

    public LocalDateTime getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(LocalDateTime executeTime) {
        this.executeTime = executeTime;
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "id=" + id +
                ", assignTime=" + assignTime +
                ", patient=" + patient +
                ", procedure=" + procedure +
                ", executor=" + executor +
                ", executeTime=" + executeTime +
                '}';
    }
}
