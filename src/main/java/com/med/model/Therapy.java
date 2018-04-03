package com.med.model;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by george on 3/9/18.
 */
public class Therapy {
    int id;
    LocalDateTime dateTime;
    String diag;
    int codeDiag;
    String notes;
    String picture;
  //  Map<Procedure, Integer> progress = new HashMap<>();
    List<Procedure> procedures = new ArrayList<>();

    public Therapy() {
    }

    public Therapy(int id, LocalDateTime dateTime, String diag, int codeDiag, String notes, String picture, List<Procedure> procedures) {
        this.id = id;
        this.dateTime = dateTime;
        this.diag = diag;
        this.codeDiag = codeDiag;
        this.notes = notes;
        this.picture = picture;
        this.procedures = procedures;
    }

    public Therapy(LocalDateTime dateTime, String diag, int codeDiag, String notes, String picture, List<Procedure> procedures) {
        this.dateTime = dateTime;
        this.diag = diag;
        this.codeDiag = codeDiag;
        this.notes = notes;
        this.picture = picture;
        this.procedures = procedures;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getDiag() {
        return diag;
    }

    public void setDiag(String diag) {
        this.diag = diag;
    }

    public int getCodeDiag() {
        return codeDiag;
    }

    public void setCodeDiag(int codeDiag) {
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

    @Override
    public String toString() {
        return "Therapy{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", diag='" + diag + '\'' +
                ", codeDiag=" + codeDiag +
                ", notes='" + notes + '\'' +
                ", picture='" + picture + '\'' +
                ", procedures=" + procedures +
                '}';
    }
}
