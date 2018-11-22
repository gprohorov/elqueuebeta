package com.med.model.balance;

/**
 * Created by george on 19.11.18.
 */
public class ProcedureZonesSum {
    String procedureZones;
    int sum;

    public ProcedureZonesSum() {
    }

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
