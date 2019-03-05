package com.med.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Usi {

	@Id
    private String id;
    private String patientId;
    private LocalDate date;
    private int doctorId;
    @Transient
    private String doctor;
    private String title;
    private String body;

    public Usi() {}

	public Usi(String patientId, LocalDate date, int doctorId, String title, String body) {
		this.patientId = patientId;
		this.date = date;
		this.doctorId = doctorId;
		this.title = title;
		this.body = body;
	}
	
	public Usi(String id, String patientId, LocalDate date, int doctorId, String title, String body) {
		this.id = id;
		this.patientId = patientId;
		this.date = date;
		this.doctorId = doctorId;
		this.title = title;
		this.body = body;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public int getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}
	
	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return String.format("Usi [id=%s, patientId=%s, date=%s, doctorId=%s, doctor=%s, title=%s, body=%s]",
			id, patientId, date, doctorId, doctor, title, body);
	}
	
}