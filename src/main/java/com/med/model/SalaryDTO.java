package com.med.model;

/**
 * Created by george on 28.10.18.
 */
public class SalaryDTO {

    private String name;
    private int doctorId;
    private int days;
    private int hours;
    private int stavka;
    private int accural;
    private int award;
    private int penalty;
    private int kredit;
    private int total;
    private int recd;
    private int rest;
    private int actual;

    public SalaryDTO(String name, int doctorId, int days, int hours, int stavka, int accural, int award, int penalty, int kredit, int total, int recd, int rest, int actual) {
        this.name = name;
        this.doctorId = doctorId;
        this.days = days;
        this.hours = hours;
        this.stavka = stavka;
        this.accural = accural;
        this.award = award;
        this.penalty = penalty;
        this.kredit = kredit;
        this.total = total;
        this.recd = recd;
        this.rest = rest;
        this.actual = actual;
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

    public int getRecd() {
        return recd;
    }

    public void setRecd(int recd) {
        this.recd = recd;
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

    public int getActual() {
        return actual;
    }

    public void setActual(int actual) {
        this.actual = actual;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    @Override
    public String toString() {
        return "SalaryDTO{" +
                "name='" + name + '\'' +
                ", doctorId=" + doctorId +
                ", days=" + days +
                ", hours=" + hours +
                ", stavka=" + stavka +
                ", accural=" + accural +
                ", award=" + award +
                ", penalty=" + penalty +
                ", kredit=" + kredit +
                ", total=" + total +
                ", recd=" + recd +
                ", rest=" + rest +
                ", actual=" + actual +
                '}';
    }
}
