package com.med.model;

/**
 * Created by george on 3/9/18.
 */
public class Procedure {
    int id;
    String name;
    int cabinet;
    String notes;
    int area;


    public Procedure(int id, String name, int cabinet) {
        this.id = id;
        this.name = name;
        this.cabinet = cabinet;
           }

    public Procedure() {
    }

    public Procedure(int id, String name, int cabinet, String notes, int area) {
        this.id = id;
        this.name = name;
        this.cabinet = cabinet;
        this.notes = notes;
        this.area = area;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCabinet() {
        return cabinet;
    }

    public void setCabinet(int cabinet) {
        this.cabinet = cabinet;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getArea() {
        return area;
    }

    public void setArea(byte area) {
        this.area = area;
    }

    @Override
    public String toString() {
        return "Procedure{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cabinet=" + cabinet +
                ", notes='" + notes + '\'' +
                ", area=" + area +
                '}';
    }
}
