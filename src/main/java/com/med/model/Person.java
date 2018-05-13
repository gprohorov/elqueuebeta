package com.med.model;

import java.time.LocalDate;

/**
 * Created by george on 3/9/18.
 */
public class Person {

    private String firstName;
    private String patronymic;
    private String lastName;
    private String cellPhone;
    private String town;
    private String address;
    private boolean gender;
    private LocalDate dateOfBirth;

    public Person() {
    }

    public Person(String firstName, String lastName) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = true;
    }

    public Person( String firstName, String patronymic, String lastName, String cellPhone, String town, String address, boolean gender, LocalDate dateOfBirth) {

        this.firstName = firstName;
        this.patronymic = patronymic;
        this.lastName = lastName;
        this.cellPhone = cellPhone;
        this.town = town;
        this.address = address;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
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

    @Override
    public String toString() {
        return "Person{" +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                 '}';
    }

}
