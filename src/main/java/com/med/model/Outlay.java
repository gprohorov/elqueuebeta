package com.med.model;

import java.time.LocalDate;

/**
 * Created by george on 22.11.18.
 */
public class Outlay {
    private  LocalDate date;
    private  int salary;
    private int extraction;
    private  int machine;
    private  int kitchen;
    private  int taxes;
    private  int other;
    private  int total;

    public Outlay() {
    }

    public Outlay(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getMachine() {
        return machine;
    }

    public void setMachine(int machine) {
        this.machine = machine;
    }

    public int getKitchen() {
        return kitchen;
    }

    public void setKitchen(int kitchen) {
        this.kitchen = kitchen;
    }

    public int getTaxes() {
        return taxes;
    }

    public void setTaxes(int taxes) {
        this.taxes = taxes;
    }

    public int getOther() {
        return other;
    }

    public void setOther(int other) {
        this.other = other;
    }

    public int getExtraction() {
        return extraction;
    }

    public void setExtraction(int extraction) {
        this.extraction = extraction;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
