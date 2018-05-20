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
    private String patient;
    private LocalDateTime dateTime;
    private Integer sum;
    private boolean cash;

    public Income() {
    }

    public Income(String patient, LocalDateTime dateTime, Integer sum, boolean cash) {
        this.patient = patient;
        this.dateTime = dateTime;
        this.sum = sum;
        this.cash = cash;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
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

    public boolean isCash() {
        return cash;
    }

    public void setCash(boolean cash) {
        this.cash = cash;
    }



    @Override
    public String toString() {
        return "Income{" +
                "id='" + id + '\'' +
                ", patient='" + patient + '\'' +
                ", dateTime=" + dateTime +
                ", sum=" + sum +
                ", cash=" + cash +
                '}';
    }




}
