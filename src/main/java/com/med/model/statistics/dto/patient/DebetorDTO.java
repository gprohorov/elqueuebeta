package com.med.model.statistics.dto.patient;

import com.med.model.Patient;

import java.time.LocalDate;

/**
 * Created by george on 29.07.18.
 */
public class DebetorDTO {

    private Patient patient;
    private LocalDate start;
    private LocalDate finish;
    private LocalDate lastPaymentDate;
    private Integer bill;
    private Integer payment;
    private Integer debt;

    public DebetorDTO() {
    }

    public DebetorDTO(Patient patient, LocalDate start, LocalDate finish, LocalDate lastPaymentDate, Integer bill, Integer payment, Integer debt) {
        this.patient = patient;
        this.start = start;
        this.finish = finish;
        this.lastPaymentDate = lastPaymentDate;
        this.bill = bill;
        this.payment = payment;
        this.debt = debt;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getFinish() {
        return finish;
    }

    public void setFinish(LocalDate finish) {
        this.finish = finish;
    }

    public LocalDate getLastPaymentDate() {
        return lastPaymentDate;
    }

    public void setLastPaymentDate(LocalDate lastPaymentDate) {
        this.lastPaymentDate = lastPaymentDate;
    }

    public Integer getBill() {
        return bill;
    }

    public void setBill(Integer bill) {
        this.bill = bill;
    }

    public Integer getPayment() {
        return payment;
    }

    public void setPayment(Integer payment) {
        this.payment = payment;
    }

    public Integer getDebt() {
        return debt;
    }

    public void setDebt(Integer debt) {
        this.debt = debt;
    }
}
