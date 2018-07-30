package com.med.model.statistics.dto.patient;

import com.med.model.Patient;

import java.time.LocalDate;

/**
 * Created by george on 29.07.18.
 */
public class PatientDTO {

    private Patient patient;
    private LocalDate start;
    private LocalDate finish;
    private Integer days;
    private Integer procedures;
    private Integer zones;
    private Integer bill;
    private Integer cash;
    private Integer card;
    private Integer discount;
    private Integer debt;

    public PatientDTO() {}

    public Integer getCash() {
        return cash;
    }

    public void setCash(Integer cash) {
        this.cash = cash;
    }

    public Integer getCard() {
        return card;
    }

    public void setCard(Integer card) {
        this.card = card;
    }

    public PatientDTO(Patient patient, LocalDate start, LocalDate finish, Integer days, Integer procedures, Integer zones, Integer bill, Integer cash, Integer card, Integer discount, Integer debt) {
        this.patient = patient;
        this.start = start;
        this.finish = finish;
        this.days = days;
        this.procedures = procedures;
        this.zones = zones;
        this.bill = bill;
        this.cash = cash;
        this.card = card;
        this.discount = discount;
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

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Integer getProcedures() {
        return procedures;
    }

    public void setProcedures(Integer procedures) {
        this.procedures = procedures;
    }

    public Integer getZones() {
        return zones;
    }

    public void setZones(Integer zones) {
        this.zones = zones;
    }

    public Integer getBill() {
        return bill;
    }

    public void setBill(Integer bill) {
        this.bill = bill;
    }


    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getDebt() {
        return debt;
    }

    public void setDebt(Integer debt) {
        this.debt = debt;
    }
}
