package com.med.model;

public class PatientTalonTherapy {

    private Patient patient;
    private Talon talon;
    private Therapy therapy;

    public PatientTalonTherapy(Patient patient, Talon talon, Therapy therapy) {
        this.patient = patient;
        this.talon = talon;
        this.therapy = therapy;
    }

    public PatientTalonTherapy(Talon talon) {
        this.talon = talon;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Talon getTalon() {
        return talon;
    }

    public void setTalon(Talon talon) {
        this.talon = talon;
    }

    public Therapy getTherapy() {
        return therapy;
    }

    public void setTherapy(Therapy therapy) {
        this.therapy = therapy;
    }
}