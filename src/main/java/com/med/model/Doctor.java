package com.med.model;

import org.bson.types.ObjectId;
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

    private String firstName;
    private String patronymic;
    private String lastName;
    private String speciality;
    private String cellPhone;
    private List<Integer> procedureIds = new ArrayList<>();

    public List<Integer> getProcedureIds() {
        return procedureIds;
    }

    public void setProcedureIds(List<Integer> procedureIds) {
        this.procedureIds = procedureIds;
    }

    @Nullable
    private ObjectId userId;
//    private User user;

    public Doctor() {
    }

    public Doctor(int id, String firstName, String patronymic, String lastName, String speciality, String cellPhone) {
        this.id = id;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.lastName = lastName;
        this.speciality = speciality;
        this.cellPhone = cellPhone;
        this.userId = null;
    }

    public Doctor(String firstName, String patronymic, String lastName, String speciality, String cellPhone) {
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.lastName = lastName;
        this.speciality = speciality;
        this.cellPhone = cellPhone;
        this.userId = null;
    }

    public ObjectId getUserId() {
        return userId;
    }

//    public void setUserId(ObjectId userId) {
//        this.userId = userId;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", lastName='" + lastName + '\'' +
                ", speciality='" + speciality + '\'' +
                ", cellPhone='" + cellPhone + '\'' +
                '}';
    }
}
