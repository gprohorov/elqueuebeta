package com.med.model.statistics.dto.doctor;

public class DoctorProcedureZoneFee {
	
    private String name;
    private Integer proceduraCount;
    private Long zonesCount;
    private Long fee;

    public DoctorProcedureZoneFee() {}

    public DoctorProcedureZoneFee(String name, Integer proceduraCount, Long zonesCount, Long fee) {
        this.name = name;
        this.proceduraCount = proceduraCount;
        this.zonesCount = zonesCount;
        this.fee = fee;
    }

    public Long getFee() {
        return fee;
    }

    public void setFee(Long fee) {
        this.fee = fee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getProceduraCount() {
        return proceduraCount;
    }

    public void setProceduraCount(Integer proceduraCount) {
        this.proceduraCount = proceduraCount;
    }

    public Long getZonesCount() {
        return zonesCount;
    }

    public void setZonesCount(Long zonesCount) {
        this.zonesCount = zonesCount;
    }
}