package com.med.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by george on 3/9/18.
 */
@Document
public class Doctor {

    @Id
    private int id;

    private String fullName;

    private String speciality;
    private String cellPhone;
    private List<Integer> procedureIds = new ArrayList<>();
    private int rate;
    public List<Integer> getProcedureIds() {
        return procedureIds;
    }

    public void setProcedureIds(List<Integer> procedureIds) {
        this.procedureIds = procedureIds;
    }

    @Nullable
    private String userId;
//    private User user;

    public Doctor() {
    }

    public Doctor(String fullName, String speciality, String cellPhone, List<Integer> procedureIds, String userId) {
        this.fullName = fullName;
        this.speciality = speciality;
        this.cellPhone = cellPhone;
        this.procedureIds = procedureIds;
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
