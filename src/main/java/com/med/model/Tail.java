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
    private Patient patientOnProcedure = null;
    private boolean vacant;
    private ProcedureType procedureType;

    public Tail() {
    }

    public Tail(int procedureId, String procedureName, ProcedureType procedureType, List<Patient> patients, boolean vacant) {
        this.procedureId = procedureId;
        this.procedureName = procedureName;
        this.patients = patients;
        this.vacant = vacant;
        this.procedureType = procedureType;
    }

    public Tail(int procedureId, String procedureName) {
        this.procedureId = procedureId;
        this.procedureName = procedureName;
        this.vacant = true;
    }

    public Patient getPatientOnProcedure() {
        return patientOnProcedure;
    }

    public void setPatientOnProcedure(Patient patientOnProcedure) {
        this.patientOnProcedure = patientOnProcedure;
    }

    public boolean isVacant() {
        return vacant;
    }

    public void setVacant(boolean vacant) {
        this.vacant = vacant;
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

    public ProcedureType getProcedureType() {
        return procedureType;
    }

    public void setProcedureType(ProcedureType procedureType) {
        this.procedureType = procedureType;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    public Patient addPatient(Patient patient){
        if(!patients.contains(patient)) {
            this.patients.add(patient);
        }
        return patient;
    }

    public Patient getFirst() {
        return this.getPatients().stream().findFirst().orElse(null);
    }
}
