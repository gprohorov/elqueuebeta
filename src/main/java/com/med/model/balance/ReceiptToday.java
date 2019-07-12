package com.med.model.balance;

import java.time.LocalDate;
import java.util.List;

public class ReceiptToday {

    private String patientId;
    private String patientName;
    private LocalDate date;
    private List<ProcedureZonesSum> list; // список имен процедур + кол-во зон + сумма
    private int proceduresSum;  // сумма за все процедуры и готель ЗА СЕГОДНЯ
    private int payed;
    private int balance;
    private int discount;

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

	public int getPayed() {
		return payed;
	}

	public void setPayed(int payed) {
		this.payed = payed;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}
}