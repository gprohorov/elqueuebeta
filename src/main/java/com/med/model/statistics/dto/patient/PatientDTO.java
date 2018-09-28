package com.med.model.statistics.dto.patient;

import java.time.LocalDate;

/**
 * Created by george on 29.07.18.
 */
public class PatientDTO {

    private String name;
    private LocalDate start; //  first day of proceduring
    private LocalDate finish;   // the day of last proedure
    private Integer days;      // days of proceduring
    private Integer procedures;  // the ammount of executed procedures
    private Integer zones;
    private Integer bill;   //  bill for procedures and hotel
    private Integer cash;   //
    private Integer card;
    private Integer discount;
    private Integer donation;
    private Integer debt;



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


    public PatientDTO() {

        this.days = 0;
        this.procedures = 0;
        this.zones = 0;
        this.bill = 0;
        this.cash = 0;
        this.card = 0;
        this.discount = 0;
        this.donation = 0;
        this.debt = 0;
    }




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getDonation() {return donation;}

    public void setDonation(Integer donation) {this.donation = donation;}

    public Integer getDebt() {
        return debt;
    }

    public void setDebt(Integer debt) {
        this.debt = debt;
    }
}
