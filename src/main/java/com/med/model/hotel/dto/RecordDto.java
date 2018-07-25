package com.med.model.hotel.dto;

import com.med.model.hotel.State;

import java.time.LocalDateTime;

/**
 * Created by george on 25.07.18.
 */
public class RecordDto {

    private String patientId;
    private String desc;
    private int koikaId;
    private int price;
    private LocalDateTime start;
    private LocalDateTime finish;
    private State state;

    public RecordDto() {
    }

    public RecordDto(String patientId, String desc, int koikaId, int price, LocalDateTime start, LocalDateTime finish, State state) {
        this.patientId = patientId;
        this.desc = desc;
        this.koikaId = koikaId;
        this.price = price;
        this.start = start;
        this.finish = finish;
        this.state = state;
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

    public int getKoikaId() {
        return koikaId;
    }

    public void setKoikaId(int koikaId) {
        this.koikaId = koikaId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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
}
