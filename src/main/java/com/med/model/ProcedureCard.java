package com.med.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by george on 3/31/18.
 */
public class ProcedureCard {
    private int id;
    private int days;
    private Set<Procedure> deniedBefore = new HashSet<>();
    private Set<Procedure> deniedAfter = new HashSet<>();
    private Set<Procedure> needBefore = new HashSet<>();
    private Set<Procedure> needAfter = new HashSet<>();
    private Set<Procedure> changing = new HashSet<>();

    public ProcedureCard() {
    }

    public ProcedureCard(int id, int days, Set<Procedure> deniedBefore, Set<Procedure> deniedAfter, Set<Procedure> needBefore, Set<Procedure> needAfter, Set<Procedure> changing) {
        this.id = id;
        this.days = days;
        this.deniedBefore = deniedBefore;
        this.deniedAfter = deniedAfter;
        this.needBefore = needBefore;
        this.needAfter = needAfter;
        this.changing = changing;
    }

    public ProcedureCard(int id, int days) {
        this.id = id;
        this.days = days;
        this.deniedBefore = new HashSet<>();
        this.deniedAfter = new HashSet<>();
        this.needBefore = new HashSet<>();
        this.needAfter = new HashSet<>();
        this.changing = new HashSet<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public Set<Procedure> getDeniedBefore() {
        return deniedBefore;
    }

    public void setDeniedBefore(Set<Procedure> deniedBefore) {
        this.deniedBefore = deniedBefore;
    }

    public Set<Procedure> getDeniedAfter() {
        return deniedAfter;
    }

    public void setDeniedAfter(Set<Procedure> deniedAfter) {
        this.deniedAfter = deniedAfter;
    }

    public Set<Procedure> getNeedBefore() {
        return needBefore;
    }

    public void setNeedBefore(Set<Procedure> needBefore) {
        this.needBefore = needBefore;
    }

    public Set<Procedure> getNeedAfter() {
        return needAfter;
    }

    public void setNeedAfter(Set<Procedure> needAfter) {
        this.needAfter = needAfter;
    }

    public Set<Procedure> getChanging() {
        return changing;
    }

    public void setChanging(Set<Procedure> changing) {
        this.changing = changing;
    }
}
