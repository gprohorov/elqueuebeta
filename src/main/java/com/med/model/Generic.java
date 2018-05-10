package com.med.model;

/**
 * Created by george on 3/11/18.
 */
public class Generic {
    private int id;
    private String name;

    public Generic(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Generic() {
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
}
