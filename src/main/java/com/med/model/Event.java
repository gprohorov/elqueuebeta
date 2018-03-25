package com.med.model;

import java.time.LocalDateTime;

/**
 * Created by george on 3/9/18.
 */
public class Event {
    Integer id;
    LocalDateTime time;
    Patient patient;
    Doctor doctor;
    Procedure procedure;
    Action action;

    public Event(Integer id, LocalDateTime time, Patient patient, Doctor doctor, Procedure procedure, Action action) {
        this.id = id;
        this.time = time;
        this.patient = patient;
        this.doctor = doctor;
        this.procedure = procedure;
        this.action = action;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Procedure getProcedure() {
        return procedure;
    }

    public void setProcedure(Procedure procedure) {
        this.procedure = procedure;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", time=" + time +
                ", patient=" + patient +
                ", doctor=" + doctor +
                ", procedure=" + procedure +
                ", action=" + action +
                '}';
    }
}
