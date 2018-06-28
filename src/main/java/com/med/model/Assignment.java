package com.med.model;

/**
 * Created by george on 28.06.18.
 */
public class Assignment {
    private Procedure procedure;
    private String desc;
    private String picture;

    public Assignment(Procedure procedure, String desc, String picture) {
        this.procedure = procedure;
        this.desc = desc;
        this.picture = picture;
    }

    public Assignment() {
    }

    public Procedure getProcedure() {
        return procedure;
    }

    public void setProcedure(Procedure procedure) {
        this.procedure = procedure;
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
                "procedure=" + procedure.getName() +
                ", desc='" + desc + '\'' +
                ", picture='" + picture + '\'' +
                '}';
    }
}
