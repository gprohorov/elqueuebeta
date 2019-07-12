package com.med.model.balance;

import com.med.model.Procedure;

public class Course {

    private Procedure procedure;
    private long times;
    private long zones;
    private long summa;

    public Course() {}

    public Course(Procedure procedure, long times, long zones, long summa) {
        this.procedure = procedure;
        this.times = times;
        this.zones = zones;
        this.summa = summa;
    }

    public Procedure getProcedure() {
        return procedure;
    }

    public void setProcedure(Procedure procedure) {
        this.procedure = procedure;
    }

    public long getTimes() {
        return times;
    }

    public void setTimes(long times) {
        this.times = times;
    }

    public long getZones() {
        return zones;
    }

    public void setZones(long zones) {
        this.zones = zones;
    }

    public long getSumma() {
        return summa;
    }

    public void setSumma(long summa) {
        this.summa = summa;
    }
}