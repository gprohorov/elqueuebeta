package com.med.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by george on 3/31/18.
 */
@Document
public class Card {
    @Id
    private int id;
    private String name;
    private int days;
    private List<Procedure> deniedByDay = new ArrayList<>();
    private List<Procedure> deniedAfter = new ArrayList<>();
    private List<Procedure> needAfter = new ArrayList<>();


    public Card() {
    }

    public Card(int id, String name, int days) {
        this.id = id;
        this.name = name;
        this.days = days;
    }

    public Card(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }
}
