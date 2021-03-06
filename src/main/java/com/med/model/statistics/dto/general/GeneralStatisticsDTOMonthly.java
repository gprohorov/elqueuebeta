package com.med.model.statistics.dto.general;

import org.springframework.data.mongodb.core.mapping.Document;


@Document
public class GeneralStatisticsDTOMonthly {

    private String id;
    private String month;
    private int year;
    private int monthNumber;
    private int patients;
    private long cash;
    private long card;
    private long bill;
    private long discount;
    private long outcome;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonthNumber() {
        return monthNumber;
    }

    public void setMonthNumber(int weekNumber) {
        this.monthNumber = weekNumber;
    }

    public int getPatients() {
        return patients;
    }

    public void setPatients(int patients) {
        this.patients = patients;
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

    public long getOutcome() {
        return outcome;
    }

    public void setOutcome(long outcome) {
        this.outcome = outcome;
    }




    @Override
    public String toString() {
        return "GeneralStatisticsDTOMonthly{" +
                "id='" + id + '\'' +
                ", month='" + month + '\'' +
                ", year=" + year +
                ", patients=" + patients +
                ", cash=" + cash +
                ", card=" + card +
                ", bill=" + bill +
                ", discount=" + discount +
                ", outcome=" + outcome +
                '}';
    }
}
