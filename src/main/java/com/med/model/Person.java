package com.med.model;

import java.time.LocalDate;

/**
 * Created by george on 3/9/18.
 */
public class Person {

    private String fullName;

    private String cellPhone;
    private String town;
    private String address;
    private boolean gender;
    private LocalDate dateOfBirth;

    public Person() {
    }


    public Person(String fullName, String cellPhone, String town, String address, boolean gender, LocalDate dateOfBirth) {
        this.fullName = fullName;
        this.cellPhone = cellPhone;
        this.town = town;
        this.address = address;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }



    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }



    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

}
