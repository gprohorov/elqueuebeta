package com.med.model.hotel.dto;

import java.time.LocalDate;

import com.med.model.hotel.State;

/**
 * Created by george on 25.07.18.
 */
public class RecordDto {

    private String patientId;
    private String desc;
    private int koikaId;
    private int price;
    private String start;
    private String finish;
    private State state;

    public RecordDto() {
    }
    
    public RecordDto(String patientId, String desc, int koikaId, int price, String start, String finish, State state) {
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

    public String getStart() {
        return start;
    }
    
    public LocalDate getStartDate() {
        return LocalDate.parse(start);
    }

    public void setStart(String start) {
    	this.start = start;
    }

    public String getFinish() {
        return finish;
    }
    
    public void setFinish(String finish) {
    	this.finish = finish;
    }
    
    public LocalDate getFinishDate() {
        return LocalDate.parse(finish);
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
