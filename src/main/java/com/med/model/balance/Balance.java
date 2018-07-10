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
    private List<Accounting> accountings = new ArrayList<>();

    public Balance(String patientId, LocalDate start, LocalDate finish) {
        this.patientId = patientId;
        this.start = start;
        this.finish = finish;
    }

    public Balance() {
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

    public List<Accounting> getAccountings() {
        return accountings;
    }

    public void setAccountings(List<Accounting> accountings) {
        this.accountings = accountings;
    }
}























