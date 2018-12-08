package com.med.model.balance;

import java.time.LocalDate;
import java.util.List;

public class ReceiptToday {

    private String patientId;
    private String patientName;
    private LocalDate date;
    private List<ProcedureZonesSum> list; // список имен процедур + кол-во зон + сумма
    private int proceduresSum;  // сумма за все процедуры и готель ЗА СЕГОДНЯ
    private int debt;   //   должок
    private int totalToPay;   //  всего к оплате

    public ReceiptToday() {}

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<ProcedureZonesSum> getList() {
        return list;
    }

    public void setList(List<ProcedureZonesSum> list) {
        this.list = list;
    }

    public int getProceduresSum() {
        return proceduresSum;
    }

    public void setProceduresSum(int proceduresSum) {
        this.proceduresSum = proceduresSum;
    }

    public int getDebt() {
        return debt;
    }

    public void setDebt(int debt) {
        this.debt = debt;
    }

    public int getTotalToPay() {
        return totalToPay;
    }

    public void setTotalToPay(int totalToPay) {
        this.totalToPay = totalToPay;
    }
}