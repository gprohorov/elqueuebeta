package com.med.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Person {

    private String fullName;
    private String cellPhone;
    private String town;
    private String address;
    private boolean gender;
    private LocalDate dateOfBirth;
    private Approvance video;
    private Approvance informated;
    private LocalDateTime registrationDate = LocalDateTime.now();


    public Person() {}

    public Person(String fullName, String cellPhone, String town, String address, boolean gender, LocalDate dateOfBirth, Approvance video, Approvance informated, LocalDateTime registrationDate) {
        this.fullName = fullName;
        this.cellPhone = cellPhone;
        this.town = town;
        this.address = address;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.video = video;
        this.informated = informated;
        this.registrationDate = registrationDate;
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

    public Approvance getVideo() {
        return video;
    }

    public void setVideo(Approvance video) {
        this.video = video;
    }

    public Approvance getInformated() {
        return informated;
    }

    public void setInformated(Approvance informated) {
        this.informated = informated;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public String toString() {
        return "Person{" +
                "fullName='" + fullName + '\'' +
                ", cellPhone='" + cellPhone + '\'' +
                ", town='" + town + '\'' +
                ", address='" + address + '\'' +
                ", gender=" + gender +
                ", dateOfBirth=" + dateOfBirth +
                ", video=" + video +
                ", informated=" + informated +
                ", registrationDate=" + registrationDate +
                '}';
    }
}
