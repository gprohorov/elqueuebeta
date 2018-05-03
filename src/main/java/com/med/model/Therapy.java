package com.med.model;

import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by george on 3/9/18.
 */
public class Therapy {
   private ObjectId id;
   private LocalDateTime dateTime;
   private String diag;
   private int codeDiag;
   private String notes;
   private String picture;
   private List<Procedure> procedures = new ArrayList<>();
   private int zones;
    public Therapy() {
    }

    public Therapy( LocalDateTime dateTime, String diag, int codeDiag, String notes, String picture, List<Procedure> procedures, int zones) {
        this.dateTime = dateTime;
        this.diag = diag;
        this.codeDiag = codeDiag;
        this.notes = notes;
        this.picture = picture;
        this.procedures = procedures;
        this.zones = zones;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
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

    public int getZones() {
        return zones;
    }

    public void setZones(int zones) {
        this.zones = zones;
    }
}
