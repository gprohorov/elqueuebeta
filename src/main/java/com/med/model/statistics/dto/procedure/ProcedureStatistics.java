package com.med.model.statistics.dto.procedure;

/**
 * Created by george on 23.07.18.
 */
public class ProcedureStatistics {
    private String name;
    private Long assigned;
    private Long executed;
    private Long cancelled;
    private Long expired;
    private Long zones;
    private Long fee;


    public ProcedureStatistics() {
    }

    public ProcedureStatistics(String name, Long assigned, Long executed, Long cancelled, Long expired, Long zones, Long fee) {
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

    public Long getAssigned() {
        return assigned;
    }

    public void setAssigned(Long assigned) {
        this.assigned = assigned;
    }

    public Long getExecuted() {
        return executed;
    }

    public void setExecuted(Long executed) {
        this.executed = executed;
    }

    public Long getCancelled() {
        return cancelled;
    }

    public void setCancelled(Long cancelled) {
        this.cancelled = cancelled;
    }

    public Long getExpired() {
        return expired;
    }

    public void setExpired(Long expired) {
        this.expired = expired;
    }

    public Long getZones() {
        return zones;
    }

    public void setZones(Long zones) {
        this.zones = zones;
    }

    public Long getFee() {
        return fee;
    }

    public void setFee(Long fee) {
        this.fee = fee;
    }
}
