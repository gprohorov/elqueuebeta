package com.med.model.balance;

public class ProcedureZonesSum {
    
	String procedureZones;
    int sum;

    public ProcedureZonesSum() {}

    public ProcedureZonesSum(String procedureZones, int sum) {
        this.procedureZones = procedureZones;
        this.sum = sum;
    }

    public String getProcedureZones() {
        return procedureZones;
    }

    public void setProcedureZones(String procedureZones) {
        this.procedureZones = procedureZones;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}