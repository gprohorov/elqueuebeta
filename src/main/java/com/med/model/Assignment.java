package com.med.model;

/**
 * Created by george on 28.06.18.
 */
public class Assignment {
    private int procedureId;
    private String desc;
    private String picture;

    public Assignment() {
    }

    public Assignment(int procedureId, String desc, String picture) {
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "procedureId=" + procedureId +
                ", desc='" + desc + '\'' +
                ", picture='" + picture + '\'' +
                '}';
    }
}
