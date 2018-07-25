package com.med.model.statistics.dto.doctor;

/**
 * Created by george on 24.07.18.
 */
public class DoctorPercent {
    private String doctorName;
    private  Long percent;

    public DoctorPercent() {
    }

    public DoctorPercent(String doctorName, Long percent) {
        this.doctorName = doctorName;
        this.percent = percent;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public Long getPercent() {
        return percent;
    }

    public void setPercent(Long percent) {
        this.percent = percent;
    }
}
