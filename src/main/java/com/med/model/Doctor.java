package com.med.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.Nullable;

@Document
public class Doctor {

    @Id
    private int id;
    private String fullName;
    private String speciality;
    private String cellPhone;
    private boolean isActive;
    private int daysOff;
    private List<Integer> procedureIds = new ArrayList<>();
    private List<DoctorProcedureProcent> percents =  new ArrayList<>();
    private int rate;
    private int kredit;
    @Nullable
    private String userId;
    
    public Doctor() {}

    public Doctor(String fullName, String speciality, String cellPhone,
    		List<Integer> procedureIds, String userId) {
        this.fullName = fullName;
        this.speciality = speciality;
        this.cellPhone = cellPhone;
        this.procedureIds = procedureIds;
        this.userId = userId;
    }

    public Doctor(String fullName, String speciality, String cellPhone,
    		List<Integer> procedureIds, int rate, int kredit,  String userId) {
        this.fullName = fullName;
        this.speciality = speciality;
        this.cellPhone = cellPhone;
        this.procedureIds = procedureIds;
        this.rate = rate;
        this.kredit = kredit;
        this.userId = userId;
    }

    public Doctor(String fullName, String speciality, String cellPhone, boolean isActive, List<Integer> procedureIds, int rate, int kredit, String userId) {
        this.fullName = fullName;
        this.speciality = speciality;
        this.cellPhone = cellPhone;
        this.isActive = isActive;
        this.procedureIds = procedureIds;
        this.rate = rate;
        this.kredit = kredit;
        this.userId = userId;
    }

    public int getRate() {
        return rate;
    }

    public List<Integer> getProcedureIds() {
    	return procedureIds;
    }
    
    public void setProcedureIds(List<Integer> procedureIds) {
    	this.procedureIds = procedureIds;
    }
    
    public void setRate(int rate) {
        this.rate = rate;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public int getKredit() {
        return kredit;
    }

    public void setKredit(int kredit) {
        this.kredit = kredit;
    }

    public List<DoctorProcedureProcent> getPercents() {
        return percents;
    }

    public void setPercents(List<DoctorProcedureProcent> percents) {
        this.percents = percents;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getDaysOff() {
        return daysOff;
    }

    public void setDaysOff(int daysOff) {
        this.daysOff = daysOff;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Doctor)) return false;
        Doctor doctor = (Doctor) o;
        return getId() == doctor.getId();
    }

    @Override
    public int hashCode() {
        return getId();
    }
}