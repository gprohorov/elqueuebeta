package com.med.model.balance;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Accounting {
    @Id
    private String id;
    private Integer doctorId;
    private String patientId;
    private LocalDateTime dateTime;
    private LocalDate date;
    private String talonId;
    private Integer sum = 0;
    private PaymentType payment;
    private String desc = "";
    private int koikaId;

    public Accounting() {}

    public Accounting(String patientId, LocalDateTime dateTime, Integer sum,
    		PaymentType payment, int koikaId, String desc) {
        this.patientId = patientId;
        this.dateTime = dateTime;
        this.sum = sum;
        this.payment = payment;
        this.desc = desc;
        this.koikaId = koikaId;
    }

    public Accounting(String patientId, LocalDateTime dateTime, Integer sum,
			PaymentType payment, String desc) {
        this.patientId = patientId;
        this.dateTime = dateTime;
        this.date = this.dateTime.toLocalDate();
        this.sum = sum;
        this.payment = payment;
        this.desc = desc;
    }

    public Accounting(Integer doctorId, String patientId, LocalDateTime dateTime,
    		String talonId, Integer sum, PaymentType payment, String desc) {
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.dateTime = dateTime;
        this.date = this.dateTime.toLocalDate();
        this.talonId = talonId;
        this.sum = sum;
        this.payment = payment;
        this.desc = desc;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTalonId() {
    	return talonId;
	}

    public void setTalonId(String talonId) {
    	this.talonId = talonId;
	}

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public PaymentType getPayment() {
        return payment;
    }

    public void setPayment(PaymentType payment) {
        this.payment = payment;
    }

    public int getKoikaId() {
        return koikaId;
    }

    public void setKoikaId(int koikaId) {
        this.koikaId = koikaId;
    }

    @Override
    public String toString() {
        return "Accounting{" +
                "id='" + id + '\'' +
                ", doctorId=" + doctorId +
                ", patientId='" + patientId + '\'' +
                ", dateTime=" + dateTime +
                ", date=" + date +
                ", talonId='" + talonId + '\'' +
                ", sum=" + sum +
                ", payment=" + payment +
                ", desc='" + desc + '\'' +
                ", koikaId=" + koikaId +
                '}';
    }
}