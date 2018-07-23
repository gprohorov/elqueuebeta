package com.med.model.statistics.dto;

/**
 * Created by george on 23.07.18.
 */
public class ProcedureStatistics {
    private String name;
    private Integer assigned;
    private Integer executed;
    private Integer cancelled;
    private Integer expired;
    private Integer zones;
    private Integer fee;


    public ProcedureStatistics() {
    }

    public ProcedureStatistics(String name, Integer assigned, Integer executed, Integer cancelled, Integer expired, Integer zones, Integer fee) {
        this.name = name;
        this.assigned = assigned;
        this.executed = executed;
        this.cancelled = cancelled;
        this.expired = expired;
        this.zones = zones;
        this.fee = fee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAssigned() {
        return assigned;
    }

    public void setAssigned(Integer assigned) {
        this.assigned = assigned;
    }

    public Integer getExecuted() {
        return executed;
    }

    public void setExecuted(Integer executed) {
        this.executed = executed;
    }

    public Integer getCancelled() {
        return cancelled;
    }

    public void setCancelled(Integer cancelled) {
        this.cancelled = cancelled;
    }

    public Integer getExpired() {
        return expired;
    }

    public void setExpired(Integer expired) {
        this.expired = expired;
    }

    public Integer getZones() {
        return zones;
    }

    public void setZones(Integer zones) {
        this.zones = zones;
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }






}
