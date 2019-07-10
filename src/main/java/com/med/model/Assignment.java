package com.med.model;

import java.util.ArrayList;

public class Assignment {
	
    private int procedureId;
    private String desc;
    private ArrayList<ArrayList<Object>> picture;

    public Assignment() {}

    public Assignment(int procedureId, String desc, ArrayList<ArrayList<Object>> picture) {
        this.procedureId = procedureId;
        this.desc = desc;
        this.picture = picture;
    }

    public int getProcedureId() {
        return procedureId;
    }

    public void setProcedureId(int procedureId) {
        this.procedureId = procedureId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public ArrayList<ArrayList<Object>> getPicture() {
        return picture;
    }

    public void setPicture(ArrayList<ArrayList<Object>> picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "Assignment{" + "procedureId=" + procedureId + ", desc='" + desc + '\'' +
                ", picture='" + picture + '\'' + "}";
    }
}