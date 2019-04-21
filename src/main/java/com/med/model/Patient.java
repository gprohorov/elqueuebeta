package com.med.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "patient")
public class Patient {

    @Id
    private String id;
    private Person person;
    @Transient
    @Nullable
    private Therapy therapy;
    @Transient
    private Activity activity = Activity.NON_ACTIVE;
    @Transient
    private int appointed = 10;
    @Transient
    private List<Talon> talons = new ArrayList<>();
    private LocalDateTime lastActivity;
    private LocalDateTime startActivity;
    private Status status = Status.SOCIAL;
    private int balance;
    private int discount = 0;
    private LocalDateTime registration;
    private int days = 0;
    private boolean isHotel;
    @Transient
    private Talon talon;

    public Patient() {}

    public Patient(String id, Person person, Therapy therapy, List<Talon> talons,
    		LocalDateTime lastActivity, LocalDateTime startActivity, Status status) {
        this.id = id;
        this.person = person;
        this.therapy = therapy;
        this.talons = talons;
        this.lastActivity = lastActivity;
        this.startActivity = startActivity;
        this.status = status;
    }

    public Patient(Person person, Therapy therapy, List<Talon> talons,
    		LocalDateTime lastActivity, LocalDateTime startActivity, Status status) {
        this.person = person;
        this.therapy = therapy;
        this.talons = talons;
        this.lastActivity = lastActivity;
        this.startActivity = startActivity;
        this.status = status;
        this.registration = LocalDateTime.now();
    }

    public Patient(Person person) {
        this.talons = new ArrayList<>();
        this.person = person;
        this.registration = LocalDateTime.now();
    }

    public boolean isHotel() {
        return isHotel;
    }

    public void setHotel(boolean hotel) {
        isHotel = hotel;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public Talon getTalon() {
        return talon;
    }

    public void setTalon(Talon talon) {
        this.talon = talon;
    }

    public LocalDateTime getRegistration() {
        return registration;
    }

    public void setRegistration(LocalDateTime registration) {
        this.registration = registration;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Therapy getTherapy() {
        return therapy;
    }

    public void setTherapy(Therapy therapy) {
        this.therapy = therapy;
    }

    public List<Talon> getTalons() {
        return talons;
    }

    public void setTalons(List<Talon> talons) {
        this.talons = talons;
    }

    public LocalDateTime getStartActivity() {
        return startActivity;
    }

    public void setStartActivity(LocalDateTime startActivity) {
        this.startActivity = startActivity;
    }

    public LocalDateTime getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(LocalDateTime lastActivity) {
        this.lastActivity = lastActivity;
    }

    public Status getStatus() {
        return status;
    }

    public int getStatusLevel() {
        return this.getStatus().getLevel();
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getDelta() {
    	return (this.getLastActivity() == null) ? null
			: ChronoUnit.MINUTES.between(this.getLastActivity(), LocalDateTime.now());
    }

    public int getAppointed() {
    	return (this.getTalons().isEmpty()) ? this.appointed : this.getTalons().get(0).getAppointed();
    }

    public void setAppointed(int appointed) {
        this.appointed = appointed;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Activity getActivity() {
        return this.activity;
    }

    public Activity calcActivity() {

    	// -- Anybody hear about switch/case?
    	// TODO: Rewrite this shit, bleat nahui... 
    	
        if (this.getTalons().isEmpty()) {
            return Activity.NULL;
        }

        if (this.getTalons().stream()
                .map(talon -> talon.getActivity()).anyMatch(ac -> ac.equals(Activity.INVITED))) {
        	return Activity.INVITED;
        }

        if (this.getTalons().stream()
                .map(talon -> talon.getActivity()).anyMatch(ac -> ac.equals(Activity.ON_PROCEDURE))) {
        	return Activity.ON_PROCEDURE;
        }

        if (this.getTalons().stream()
                .map(talon -> talon.getActivity()).anyMatch(ac -> ac.equals(Activity.ACTIVE))) {
        	return Activity.ACTIVE;
        }

        if (this.getTalons().stream()
                .map(talon -> talon.getActivity()).allMatch(ac -> ac.equals(Activity.NON_ACTIVE))) {
        	return Activity.NON_ACTIVE;
        }

        if (this.getTalons().stream()
                .map(talon -> talon.getActivity()).allMatch(ac -> ac.equals(Activity.TEMPORARY_NA))) {
        	return Activity.TEMPORARY_NA;
        }

        if (this.getTalons().stream()
                .map(talon -> talon.getActivity()).anyMatch(ac -> ac.equals(Activity.TEMPORARY_NA))
            &&
                this.getTalons().stream()
                .map(talon -> talon.getActivity()).noneMatch(ac -> ac.equals(Activity.ACTIVE))) {
        	return Activity.TEMPORARY_NA;
        }

        if (this.getTalons().stream()
            .filter(talon -> !talon.getActivity().equals(Activity.EXECUTED))
            .filter(talon -> !talon.getActivity().equals(Activity.CANCELED))
            .count() == 0) {
        	return Activity.GAMEOVER;
        }

        if (this.getTalons().stream()
                .map(talon -> talon.getActivity()).noneMatch(ac -> ac.equals(Activity.ACTIVE))
            &&
                this.getTalons().stream()
                .map(talon -> talon.getActivity()).anyMatch(ac -> ac.equals(Activity.NON_ACTIVE)) ) {
        	return Activity.STUCK;
        }
        
        return Activity.NULL;
    }

    public int getActivityLevel() {
        return this.getActivity().getLevel();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Patient)) return false;
        Patient patient = (Patient) o;
        return getId().equals(patient.getId());
    }

    @Override
    public int hashCode() {
        return this.getId().hashCode();
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", person=" + person +
                ", therapy=" + therapy +
                ", talons=" + talons +
                ", lastActivity=" + lastActivity +
                ", startActivity=" + startActivity +
                ", status=" + status +
                ", balance=" + balance +
                "}";
    }
}