package com.med.model.statistics.dto.general;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;


@Document
public class GeneralStatisticsDTOWeekly {

    @Id
    private String id;
    private String week;
    private int year;
    private int weekNumber;
    private int patients;
    private long cash;
    private long card;
    private long bill;
    private long discount;
    private long outcome;


    public GeneralStatisticsDTOWeekly() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(int weekNumber) {
        this.weekNumber = weekNumber;
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
        return "GeneralStatisticsDTOWeekly{" +
                "id='" + id + '\'' +
                ", week='" + week + '\'' +
                ", year=" + year +
                ", weekNumber=" + weekNumber +
                ", patients=" + patients +
                ", cash=" + cash +
                ", card=" + card +
                ", bill=" + bill +
                ", discount=" + discount +
                ", outcome=" + outcome +
                '}';
    }
}
