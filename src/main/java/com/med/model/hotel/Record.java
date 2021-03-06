package com.med.model.hotel;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Record {
	
    @Id
    private String id;
    private String patientId;
    private String desc;
    private Koika koika;
    private int price;
    private LocalDateTime start;
    private LocalDateTime finish;
    private State state;

    public Record() {}

    public Record(String patientId, String desc, Koika koika, int price,
    		LocalDateTime start, LocalDateTime finish, State state) {
        this.patientId = patientId;
        this.desc = desc;
        this.koika = koika;
        this.price = price;
        this.start = start;
        this.finish = finish;
        this.state = state;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Koika getKoika() {
        return koika;
    }

    public void setKoika(Koika koika) {
        this.koika = koika;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }
    
    public void setStringStart(String start) {
    	this.start = LocalDateTime.parse(start + "T00:00:00");
    }

    public LocalDateTime getFinish() {
        return finish;
    }

    public void setFinish(LocalDateTime finish) {
        this.finish = finish;
    }
    
    public void setStringFinish(String finish) {
    	this.finish = LocalDateTime.parse(finish + "T00:00:00");
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Record{" +
                "id='" + id + '\'' +
                ", patientId='" + patientId + '\'' +
                ", desc='" + desc + '\'' +
                ", koika=" + koika +
                ", price=" + price +
                ", start=" + start +
                ", finish=" + finish +
                ", state=" + state +
                "}";
    }
}