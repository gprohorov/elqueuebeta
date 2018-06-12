package com.med.model.hotel;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by george on 22.05.18.
 */
@Document
public class Koika {

    @Id
    private int id;
    private  String name;
    private Chamber chamber;
    private int price;

    public Koika() {
    }

    public Koika(int id, String name, Chamber chamber, int price) {
        this.id = id;
        this.name = name;
        this.chamber = chamber;
        this.price = price;
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

    public Chamber getChamber() {
        return chamber;
    }

    public void setChamber(Chamber chamber) {
        this.chamber = chamber;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
