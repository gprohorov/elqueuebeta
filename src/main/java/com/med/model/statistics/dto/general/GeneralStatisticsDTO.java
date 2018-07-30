package com.med.model.statistics.dto.general;

import java.time.LocalDate;

/**
 * Created by george on 30.07.18.
 */
public class GeneralStatisticsDTO {
    private LocalDate date;
    private int patients;
    private int doctors;
    private long  cash;
    private long  card;
    private long  bill;
    private long  discount;
    private long  debt;

    public GeneralStatisticsDTO() {
    }

    public GeneralStatisticsDTO(LocalDate date, int patients, int doctors, long cash, long card, long bill, long discount, long debt) {
        this.date = date;
        this.patients = patients;
        this.doctors = doctors;
        this.cash = cash;
        this.card = card;
        this.bill = bill;
        this.discount = discount;
        this.debt = debt;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getPatients() {
        return patients;
    }

    public void setPatients(int patients) {
        this.patients = patients;
    }

    public int getDoctors() {
        return doctors;
    }

    public void setDoctors(int doctors) {
        this.doctors = doctors;
    }

    public long getCash() {
        return cash;
    }

    public void setCash(long cash) {
        this.cash = cash;
    }

    public long getCard() {
        return card;
    }

    public void setCard(long card) {
        this.card = card;
    }

    public long getBill() {
        return bill;
    }

    public void setBill(long bill) {
        this.bill = bill;
    }

    public long getDiscount() {
        return discount;
    }

    public void setDiscount(long discount) {
        this.discount = discount;
    }

    public long getDebt() {
        return debt;
    }

    public void setDebt(long debt) {
        this.debt = debt;
    }

    @Override
    public String toString() {
        return "GeneralStatisticsDTO{" +
                "date=" + date +
                ", patients=" + patients +
                ", doctors=" + doctors +
                ", cash=" + cash +
                ", card=" + card +
                ", bill=" + bill +
                ", discount=" + discount +
                ", debt=" + debt +
                '}';
    }
}
