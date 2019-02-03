package com.med.model.statistics.dto.doctor;

import java.time.LocalDateTime;
import java.util.List;

public class DoctorCurrentStatistics {

    private String name;
    private List<ProcedureCount> procedureMap;
    private Long zonesCount;
    private List<String> patients;
    private String currentPatient;
    private LocalDateTime startWork;
    private LocalDateTime lastActivity;
    private Long fee;

    public DoctorCurrentStatistics() {}

    public DoctorCurrentStatistics(String name, List<ProcedureCount> procedureMap,
    		Long zonesCount, List<String> patients, String currentPatient,
    		LocalDateTime startWork, LocalDateTime lastActivity, Long fee) {
        this.name = name;
        this.procedureMap = procedureMap;
        this.zonesCount = zonesCount;
        this.patients = patients;
        this.currentPatient = currentPatient;
        this.startWork = startWork;
        this.lastActivity = lastActivity;
        this.fee = fee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProcedureCount> getProcedureMap() {
        return procedureMap;
    }

    public void setProcedureMap(List<ProcedureCount> procedureMap) {
        this.procedureMap = procedureMap;
    }

    public Long getZonesCount() {
        return zonesCount;
    }

    public void setZonesCount(Long zonesCount) {
        this.zonesCount = zonesCount;
    }

    public List<String> getPatients() {
        return patients;
    }

    public void setPatients(List<String> patients) {
        this.patients = patients;
    }

    public String getCurrentPatient() {
        return currentPatient;
    }

    public void setCurrentPatient(String currentPatient) {
        this.currentPatient = currentPatient;
    }

    public LocalDateTime getStartWork() {
        return startWork;
    }

    public void setStartWork(LocalDateTime startWork) {
        this.startWork = startWork;
    }

    public LocalDateTime getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(LocalDateTime lastActivity) {
        this.lastActivity = lastActivity;
    }

    public Long getFee() {
        return fee;
    }

    public void setFee(Long fee) {
        this.fee = fee;
    }
}