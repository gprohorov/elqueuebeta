package com.med.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by george on 3/9/18.
 */
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
    private String picture;
   // private List<Point> hord;
    private List<Procedure> procedures = new ArrayList<>();
    private int days;

    public Therapy(String patientId, LocalDateTime start, LocalDateTime finish, String diag, String codeDiag, String notes, String picture, List<Procedure> procedures, int days) {
        this.patientId = patientId;
        this.start = start;
        this.finish = finish;
        this.diag = diag;
        this.codeDiag = codeDiag;
        this.notes = notes;
        this.picture = picture;
        this.procedures = procedures;
        this.days = days;
    }

    public Therapy(String patientId, String diag, List<Procedure> procedures, int days) {

        this.start = LocalDateTime.now();
        this.patientId = patientId;
        this.diag = diag;
        this.procedures = procedures;
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public List<Procedure> getProcedures() {
        return procedures;
    }

    public void setProcedures(List<Procedure> procedures) {
        this.procedures = procedures;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    @Override
    public String toString() {
        return "Therapy{" +
                "id='" + id + '\'' +
                ", patientId='" + patientId + '\'' +
                ", start=" + start +
                ", finish=" + finish +
                ", diag='" + diag + '\'' +
                ", codeDiag='" + codeDiag + '\'' +
                ", notes='" + notes + '\'' +
                ", picture='" + picture + '\'' +
                ", procedures=" + procedures +
                ", days=" + days +
                '}';
    }
}
