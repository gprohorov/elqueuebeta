package com.med.model.balance;

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
    private Integer sum =0;
    private  Integer discount=0;
    private PaymentType payment;

    public Income() {
    }

    public Income(String patientId, Integer sum, Integer discount, PaymentType payment) {
        this.patientId = patientId;
        this.sum = sum;
        this.discount = discount;
        this.payment = payment;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
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

    @Override
    public String toString() {
        return "Income{" +
                "id='" + id + '\'' +
                ", patientId='" + patientId + '\'' +
                ", dateTime=" + dateTime +
                ", sum=" + sum +
                ", payment=" + payment +
                '}';
    }
}
