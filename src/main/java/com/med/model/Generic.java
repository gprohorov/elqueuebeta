package com.med.model;

import org.bson.types.ObjectId;

public class Generic {
	
    private ObjectId id;
    private String name;
    
    public Generic() {}

    public Generic(ObjectId id, String name) {
        this.id = id;
        this.name = name;
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