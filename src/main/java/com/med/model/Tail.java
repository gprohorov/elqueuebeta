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
    private int vacancies=1;
    private boolean vacant;

    public Tail() {
    }

    public Tail(int procedureId, String procedureName, List<Patient> patients) {
        this.procedureId = procedureId;
        this.procedureName = procedureName;
        this.patients = patients;
        this.vacancies = 1;
        this.vacant = true;
    }

    public Tail(int procedureId, String procedureName) {
        this.procedureId = procedureId;
        this.procedureName = procedureName;
        this.vacancies = 1;
        this.vacant = true;
    }

    public Tail(int procedureId, String procedureName, int vacancies) {
        this.procedureId = procedureId;
        this.procedureName = procedureName;
        this.vacancies = vacancies;
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

    public int getVacancies() {
        return vacancies;
    }

    public void setVacancies(int vacancies) {
        this.vacancies = vacancies;
    }
}
