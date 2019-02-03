package com.med.model.statistics.dto.doctor;

import java.time.LocalDate;

/**
 * Created by george on 03.01.19.
 */
public class DoctorPeriodSalary {
    private int doctorId;
    private String name;
    private LocalDate from;
    private LocalDate to;
    private int days;
    private int hours;
    private int stavka;
    private int accural;
    private int total;

    public DoctorPeriodSalary() {
    }

    public DoctorPeriodSalary(int doctorId, LocalDate from, LocalDate to) {
        this.doctorId = doctorId;
        this.from = from;
        this.to = to;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return to;
    }

    public void setTo(LocalDate to) {
        this.to = to;
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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }
}
