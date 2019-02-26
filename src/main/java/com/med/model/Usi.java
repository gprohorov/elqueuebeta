package com.med.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.bson.types.ObjectId;

public class Usi {

	private ObjectId id = ObjectId.get();
	private LocalDateTime created = LocalDateTime.now();
    private LocalDate date;
    private int doctorId;
    private String title;
    private String doc;

    public Usi() {}

	public Usi(LocalDate date, int doctorId, String title, String doc) {
		this.date = date;
		this.doctorId = doctorId;
		this.title = title;
		this.doc = doc;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDoc() {
		return doc;
	}

	public void setDoc(String doc) {
		this.doc = doc;
	}

	@Override
	public String toString() {
		return String.format("Usi [created=%s, date=%s, doctorId=%s, title=%s, doc=%s]", 
			created, date, doctorId, title, doc);
	}
	
}