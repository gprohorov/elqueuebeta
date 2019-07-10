package com.med.model;

public class PatientRegDTO {
	
    private Person person;
    private int procedureId;
    private String date;
    private boolean activate;
    private int appointed;

    public PatientRegDTO() {}

    public PatientRegDTO(Person person, int procedureId, String date, boolean activate, int appointed) {
        this.person = person;
        this.procedureId = procedureId;
        this.date = date;
        this.activate = activate;
        this.appointed = appointed;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public int getProcedureId() {
        return procedureId;
    }

    public void setProcedureId(int procedureId) {
        this.procedureId = procedureId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isActivate() {
        return activate;
    }

    public void setActivate(boolean activate) {
        this.activate = activate;
    }

    public int getAppointed() {
        return appointed;
    }

    public void setAppointed(int appointed) {
        this.appointed = appointed;
    }
}