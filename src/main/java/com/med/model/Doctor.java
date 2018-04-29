package com.med.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private int userId;
    private User user;

    public Doctor() {
    }

    public Doctor(int id, String firstName, String patronymic, String lastName, String speciality, String cellPhone, int user_id, User user) {
        this.id = id;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.lastName = lastName;
        this.speciality = speciality;
        this.cellPhone = cellPhone;
        this.userId = user_id;
        this.user = user;
    }

    public Doctor(int id, String firstName, String patronymic, String lastName, String speciality, String cellPhone) {
        this.id = id;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.lastName = lastName;
        this.speciality = speciality;
        this.cellPhone = cellPhone;
    }

    public Doctor(String firstName, String patronymic, String lastName, String speciality, String cellPhone) {
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.lastName = lastName;
        this.speciality = speciality;
        this.cellPhone = cellPhone;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

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
