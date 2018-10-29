package com.med.model;

/**
 * Created by george on 28.10.18.
 */
public class SalaryDTO {

    private String name;
    private int days;
    private int stavka;
    private int accural;
    private int award;
    private int penalty;
    private int kredit;
    private int rest;

    private int total;

    public SalaryDTO(int days, String name, int stavka, int accural, int award, int penalty, int kredit, int total) {
        this.days = days;
        this.name = name;
        this.stavka = stavka;
        this.accural = accural;
        this.award = award;
        this.penalty = penalty;
        this.kredit = kredit;
        this.total = total;
    }

    public SalaryDTO() {
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStavka() {
        return stavka;
    }

    public void setStavka(int stavka) {
        this.stavka = stavka;
    }

    public int getAccural() {
        return accural;
    }

    public void setAccural(int accural) {
        this.accural = accural;
    }

    public int getAward() {
        return award;
    }

    public void setAward(int award) {
        this.award = award;
    }

    public int getPenalty() {
        return penalty;
    }

    public void setPenalty(int penalty) {
        this.penalty = penalty;
    }

    public int getKredit() {
        return kredit;
    }

    public void setKredit(int kredit) {
        this.kredit = kredit;
    }

    public int getRest() {
        return rest;
    }

    public void setRest(int rest) {
        this.rest = rest;
    }

    public int getTotal() {
        return total;
    }
    public void setTotal(int total) {
        this.total = total;
    }

}