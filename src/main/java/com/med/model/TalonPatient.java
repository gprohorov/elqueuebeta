package com.med.model;

public final class TalonPatient {
	
    private Talon talon;
    private Patient patient;

    public TalonPatient(Talon talon, Patient patient) {
        this.talon = talon;
        this.patient = patient;
    }

    public Talon getTalon() {
        return talon;
    }

    public Patient getPatient() {
        return patient;
    }
}