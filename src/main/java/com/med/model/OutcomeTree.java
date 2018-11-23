package com.med.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Null;
import java.time.LocalDateTime;

/**
 * Created by george on 22.11.18.
 */
@Document
public class OutcomeTree {
    @Id
    private String id;
    private LocalDateTime createTime = LocalDateTime.now();
    private String name;
    @Nullable
    private String catID;

    public OutcomeTree(String name, String catID) {
        this.name = name;
        this.catID = catID;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCatID() {
        return catID;
    }

    public void setCatID(String catID) {
        this.catID = catID;
    }
}
