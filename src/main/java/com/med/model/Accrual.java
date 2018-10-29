package com.med.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * Created by george on 18.10.18.
 */
//@Document(collection = "accrual" )
public class Accrual {
    private String id;
    private LocalDateTime dateTime;
    private String talonId;
    private int doctorId;
    private String desc;
    private int sum;

    public Accrual() {
    }

    public Accrual(LocalDateTime dateTime, String talonId, int doctorId, String desc, int sum) {
        this.dateTime = dateTime;
        this.talonId = talonId;
        this.doctorId = doctorId;
        this.desc = desc;
        this.sum = sum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getTalonId() {
        return talonId;
    }

    public void setTalonId(String talonId) {
        this.talonId = talonId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}
