package com.med.model.balance;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by george on 14.06.18.
 */
public class Balance {

    private String patientId;
    private LocalDate start;
    private LocalDate finish;
    private List<Course> courses = new ArrayList<>();
    private int hotelSum;
    private int payment;
    private int discount;
    private int summary;
    private int sumForProcedures;

    public Balance() {
    }

    public Balance(String patientId, LocalDate start, LocalDate finish) {
        this.patientId = patientId;
        this.start = start;
        this.finish = finish;
    }

    public int getSumForProcedures() {
        return sumForProcedures;
    }

    public void setSumForProcedures(int sumForProcedures) {
        this.sumForProcedures = sumForProcedures;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
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

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public int getHotelSum() {
        return hotelSum;
    }

    public void setHotelSum(int hotelSum) {
        this.hotelSum = hotelSum;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getSummary() {

        return  this.getDiscount() + this.getHotelSum() + this.getSumForProcedures()
                +this.getPayment();
    }

    public void setSummary(int summary) {
        this.summary = summary;
    }
}























