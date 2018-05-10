package com.med.model;

import org.bson.types.ObjectId;
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
    private ObjectId id;
    private int personId;
    private LocalDateTime start;
    private LocalDateTime finish;
    private String diag;
    private int codeDiag;
    private String notes;
    private String picture;
    private List<Procedure> procedures = new ArrayList<>();
    private int zones;


    public Therapy() {
    }

    public Therapy(int personId, LocalDateTime start, LocalDateTime finish, String diag, int codeDiag, String notes, String picture, List<Procedure> procedures, int zones) {
        this.personId = personId;
        this.start = start;
        this.finish = finish;
        this.diag = diag;
        this.codeDiag = codeDiag;
        this.notes = notes;
        this.picture = picture;
        this.procedures = procedures;
        this.zones = zones;
    }

    public Therapy(int personId, LocalDateTime start, LocalDateTime finish, String diag, int codeDiag, String notes, String picture, int zones) {
        this.personId = personId;
        this.start = start;
        this.finish = finish;
        this.diag = diag;
        this.codeDiag = codeDiag;
        this.notes = notes;
        this.picture = picture;
        this.zones = zones;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
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

    @Override
    public String toString() {
        return "Therapy{" +
                "id=" + id +
                ", personId=" + personId +
                ", start=" + start +
                ", finish=" + finish +
                ", diag='" + diag + '\'' +
                ", codeDiag=" + codeDiag +
                ", notes='" + notes + '\'' +
                ", picture='" + picture + '\'' +
                ", procedures=" + procedures +
                ", zones=" + zones +
                '}';
    }
}
