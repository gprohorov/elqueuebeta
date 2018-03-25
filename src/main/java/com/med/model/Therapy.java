package com.med.model;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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
    Map<Procedure, Integer> progress = new HashMap<>();

    public Therapy() {
    }

    public Therapy(int id, LocalDateTime dateTime, String diag, int codeDiag, String notes, String picture, Map<Procedure, Integer> progress) {
        this.id = id;
        this.dateTime = dateTime;
        this.diag = diag;
        this.codeDiag = codeDiag;
        this.notes = notes;
        this.picture = picture;
        this.progress = progress;
    }

    public Therapy(LocalDateTime dateTime, String diag, int codeDiag, String notes, String picture, Map<Procedure, Integer> progress) {
        this.dateTime = dateTime;
        this.diag = diag;
        this.codeDiag = codeDiag;
        this.notes = notes;
        this.picture = picture;
        this.progress = progress;
    }

    public Therapy(LocalDateTime dateTime, String diag, int codeDiag, String notes, String picture) {
        this.dateTime = dateTime;
        this.diag = diag;
        this.codeDiag = codeDiag;
        this.notes = notes;
        this.picture = picture;
        this.progress = Collections.emptyMap();
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

    public Map<Procedure, Integer> getProgress() {
        return progress;
    }

    public void setProgress(Map<Procedure, Integer> progress) {
        this.progress = progress;
    }
}
