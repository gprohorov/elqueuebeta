package com.med.model;

import org.bson.types.ObjectId;

/**
 * Created by george on 3/11/18.
 */
public class Generic {
    private ObjectId id;
    private String name;

    public Generic(ObjectId id, String name) {
        this.id = id;
        this.name = name;
    }

    public Generic() {
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
