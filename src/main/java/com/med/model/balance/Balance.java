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
  //  private List<Course> courses = new ArrayList<>();
    private List<Accounting> payments = new ArrayList<>();
    private List<Accounting> discounts = new ArrayList<>();
    private List<Accounting> bills = new ArrayList<>();
    private int hotelSum;
    private int payment;
    private int discount;
    private int sum;
    private int sumForProcedures;

    public int getSum() {
        return this.getPayment() + this.getDiscount() - this.getHotelSum() - this.getSumForProcedures();
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public Balance() {
    }

    public Balance(String patientId, LocalDate start, LocalDate finish) {
        this.patientId = patientId;
        this.start = start;
        this.finish = finish;
    }

    public List<Accounting> getPayments() {
        return payments;
    }

    public void setPayments(List<Accounting> payments) {
        this.payments = payments;
    }

    public List<Accounting> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<Accounting> discounts) {
        this.discounts = discounts;
    }

    public List<Accounting> getBills() {
        return bills;
    }

    public void setBills(List<Accounting> bills) {
        this.bills = bills;
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
/*

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
*/

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


}























