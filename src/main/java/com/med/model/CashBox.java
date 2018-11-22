package com.med.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * Created by george on 31.10.18.
 */
@Document
public class CashBox {
    @Id
    private String id;
    private LocalDateTime dateTime = LocalDateTime.now();
    private String patientId;
    private int doctorId;
    private CashType type;
    private String desc;
    private int sum;

    public CashBox(LocalDateTime dateTime, String patientId, int doctorId, String desc, int sum) {
        this.dateTime = dateTime;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.desc = desc;
        this.sum = sum;
    }

    public CashBox(LocalDateTime dateTime, String patientId, int doctorId, CashType type, String desc, int sum) {
        this.dateTime = dateTime;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.type = type;
        this.desc = desc;
        this.sum = sum;
    }
    
    public CashBox(CashType type, String desc, int sum) {
    	this.type = type;
    	this.desc = desc;
    	this.sum = sum;
    }

    public CashBox() {
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

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public CashType getType() {
        return type;
    }

    public void setType(CashType type) {
        this.type = type;
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
