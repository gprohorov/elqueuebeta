package com.med.model.balance;

import com.med.model.Patient;
import com.med.model.statistics.dto.procedure.ProcedureReceipt;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by george on 28.08.18.
 */
public class Receipt {
    String number;
    Patient patient;
    LocalDate start;
    LocalDate finish;

    List<ProcedureReceipt> list;
    int sum;
    int discount;
    int hotel;
    int tips;

    public int getHotel() {
        return hotel;
    }

    public int getTips() {
        return tips;
    }

    public void setTips(int tips) {
        this.tips = tips;
    }

    public void setHotel(int hotel) {
        this.hotel = hotel;
    }

    public Receipt() {
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }



    public List<ProcedureReceipt> getList() {
        return list;
    }

    public void setList(List<ProcedureReceipt> list) {
        this.list = list;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
}
