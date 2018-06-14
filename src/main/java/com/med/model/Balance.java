package com.med.model;

import java.time.LocalDate;
import java.util.HashMap;

/**
 * Created by george on 14.06.18.
 */
public class Balance {

    private String patientId;
    private LocalDate start;
    private LocalDate finish;
    private HashMap<Procedure, Integer> procedures;
    private int hotelSum;
    private int avance;
    private int discont;
    private int summary;

    public Balance() {
    }

    public Balance(String patientId, LocalDate start, LocalDate finish) {
        this.patientId = patientId;
        this.start = start;
        this.finish = finish;
    }

    public int getAvance() {
        return avance;
    }

    public void setAvance(int avance) {
        this.avance = avance;
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

    public HashMap<Procedure, Integer> getProcedures() {
        return procedures;
    }

    public void setProcedures(HashMap<Procedure, Integer> procedures) {
        this.procedures = procedures;
    }

    public int getHotelSum() {
        return hotelSum;
    }

    public void setHotelSum(int hotelSum) {
        this.hotelSum = hotelSum;
    }

    public int getDiscont() {
        return discont;
    }

    public void setDiscont(int discont) {
        this.discont = discont;
    }

    public int getSummary() {
        return summary;
    }

    public void setSummary(int summary) {
        this.summary = summary;
    }
}























