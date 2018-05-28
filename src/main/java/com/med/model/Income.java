package com.med.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * Created by george on 12.05.18.
 */
@Document
public class Income {
    @Id
    private String id;
    private String patientId;
    private LocalDateTime dateTime;
    private Integer sum;
    private boolean cashLess;

    public Income() {
    }

    public Income(String patientId, LocalDateTime dateTime, Integer sum, boolean cashLess) {
        this.patientId = patientId;
        this.dateTime = dateTime;
        this.sum = sum;
        this.cashLess = cashLess;
    }

    public Income(String patientId, Integer sum, boolean cashLess) {
        this.patientId = patientId;
        this.dateTime = LocalDateTime.now();
        this.sum = sum;
        this.cashLess = cashLess;
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

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }


    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public boolean isCashLess() {
        return cashLess;
    }

    public void setCashLess(boolean cashLess) {
        this.cashLess = cashLess;
    }

}
