package com.med.model;

import com.med.model.SalaryType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * Created by george on 28.10.18.
 */
@Document(collection = "salary" )
public class Salary {
    @Id
    private String id;
    private int doctorId;
    private LocalDateTime dateTime;
    private SalaryType type;
    private int sum;

    public Salary() {
    }

    public Salary(int doctorId, LocalDateTime dateTime, SalaryType type, int sum) {
        this.doctorId = doctorId;
        this.dateTime = dateTime;
        this.type = type;
        this.sum = sum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public SalaryType getType() {
        return type;
    }

    public void setType(SalaryType type) {
        this.type = type;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}
