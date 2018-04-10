package com.med.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by george on 10.04.18.
 */
public class Tail {
    private int procedureId;
    private String procedureName;
    private List<Patient> patients = new ArrayList<>();


    public Tail() {
    }

    public Tail(int procedureId, String procedureName, List<Patient> patients) {
        this.procedureId = procedureId;
        this.procedureName = procedureName;
        this.patients = patients;
    }

    public Tail(int procedureId, String procedureName) {
        this.procedureId = procedureId;
        this.procedureName = procedureName;
    }

    public int getProcedureId() {
        return procedureId;
    }

    public void setProcedureId(int procedureId) {
        this.procedureId = procedureId;
    }

    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    public Patient addPatient(Patient patient){
        this.patients.add(patient);
        return patient;
    }
}
