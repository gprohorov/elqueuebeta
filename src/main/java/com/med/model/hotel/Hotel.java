package com.med.model.hotel;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * Created by george on 30.05.18.
 */
@Document
public class Hotel {
    @Id
    private String id;

    private String patientId;
    private String desc;
    private Koika koika;
    private LocalDateTime start;
    private LocalDateTime finish;
    private State state;

    public Hotel() {
    }

    public Hotel( String patientId, String desc, Koika koika, LocalDateTime start, LocalDateTime finish, State state) {
        this.patientId = patientId;
        this.desc = desc;
        this.koika = koika;
        this.start = start;
        this.finish = finish;
        this.state = state;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Koika getKoika() {
        return koika;
    }

    public void setKoika(Koika koika) {
        this.koika = koika;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getFinish() {
        return finish;
    }

    public void setFinish(LocalDateTime finish) {
        this.finish = finish;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "id='" + id + '\'' +
                ", patientId='" + patientId + '\'' +
                ", desc='" + desc + '\'' +
                ", koika=" + koika +
                ", start=" + start +
                ", finish=" + finish +
                ", state=" + state +
                '}';
    }
}
