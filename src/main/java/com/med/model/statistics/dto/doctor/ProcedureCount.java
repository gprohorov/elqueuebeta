package com.med.model.statistics.dto.doctor;

/**
 * Created by george on 20.08.18.
 */
public class ProcedureCount {
    String procedureName;
    Long count;

    public ProcedureCount() {
    }

    public ProcedureCount(String procedureName, Long count) {
        this.procedureName = procedureName;
        this.count = count;
    }

    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
