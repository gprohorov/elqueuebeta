package com.med.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.Nullable;

@Document
public class CashBox {
	
    @Id
    private String id;
    private LocalDateTime dateTime = LocalDateTime.now();
    private String patientId;
    private int doctorId;
    private CashType type;
    @Nullable
    private String itemId;
    private String desc;
    private int sum;
    @Transient
    private String doctor;
    @Transient
    private String patient;
    
    public CashBox() {}

    public CashBox(LocalDateTime dateTime, String patientId, int doctorId, String desc, int sum) {
        this.dateTime = dateTime;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.desc = desc;
        this.sum = sum;
    }

    public CashBox(LocalDateTime dateTime, String patientId, int doctorId,
    		CashType type, String desc, int sum) {
        this.dateTime = dateTime;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.type = type;
        this.desc = desc;
        this.sum = sum;
    }

    public CashBox(int doctorId, String itemId, String desc, int sum) {
        this.doctorId = doctorId;
        this.itemId = itemId;
        this.desc = desc;
        this.sum = sum;
    }

    public CashBox(CashType type, String desc, int sum) {
    	this.type = type;
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

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
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

	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}

	public String getPatient() {
		return patient;
	}

	public void setPatient(String patient) {
		this.patient = patient;
	}

    @Override
    public String toString() {
        return "CashBox{" +
                "id='" + id + '\'' +
                ", dateTime=" + dateTime +
                ", patientId='" + patientId + '\'' +
                ", doctorId=" + doctorId +
                ", type=" + type +
                ", itemId='" + itemId + '\'' +
                ", desc='" + desc + '\'' +
                ", sum=" + sum +
                ", doctor='" + doctor + '\'' +
                ", patient='" + patient + '\'' +
                '}';
    }
}