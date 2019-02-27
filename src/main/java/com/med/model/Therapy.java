package com.med.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Therapy {

    @Id
    private String id;
    private String patientId;
    private LocalDateTime start;
    private LocalDateTime finish;
    private String diag;
    private String codeDiag;
    private String notes;
    private List<Assignment> assignments;
    private List<Usi> usi;
    private int days;
    @Transient
    private Boolean manualTherapy;
    
    public Therapy() {}

    public Therapy(String patientId, String diag, String codeDiag, String notes,
    		List<Assignment> assignments, int days) {
        this.patientId = patientId;
        this.start = LocalDateTime.now();
        this.diag = diag;
        this.codeDiag = codeDiag;
        this.notes = notes;
        this.assignments = assignments;
        this.days = days;
    }

    public Therapy(String patientId, List<Assignment> assignments, int days) {
        this.patientId = patientId;
        this.start = LocalDateTime.now();
        this.assignments = assignments;
        this.days = days;
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

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getFinish() {
        return finish;
    }

    public void setFinish(LocalDateTime finish) {
        this.finish = finish;
    }

    public String getDiag() {
        return diag;
    }

    public void setDiag(String diag) {
        this.diag = diag;
    }

    public String getCodeDiag() {
        return codeDiag;
    }

    public void setCodeDiag(String codeDiag) {
        this.codeDiag = codeDiag;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    public List<Usi> getUsi() {
		return usi;
	}

	public void setUsi(List<Usi> usi) {
		this.usi = usi;
	}

	public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public Boolean getManualTherapy() {
		return manualTherapy;
	}

	public void setManualTherapy(Boolean manualTherapy) {
		this.manualTherapy = manualTherapy;
	}

	@Override
	public String toString() {
		return String.format("Therapy [id=%s, patientId=%s, start=%s, finish=%s, diag=%s,"
			+ " codeDiag=%s, notes=%s, assignments=%s, usi=%s, days=%s, manualTherapy=%s]",
			id, patientId, start, finish, diag, codeDiag, notes, assignments, usi, days, manualTherapy);
	}
	
}