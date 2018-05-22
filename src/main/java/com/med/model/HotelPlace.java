package com.med.model;

/**
 * Created by george on 22.05.18.
 */
public class HotelPlace {
    private int id;
    private  int name;
    private Chamber chamber;
    private int price;

    public HotelPlace() {
    }

    public HotelPlace(int id, int name, Chamber chamber, int price) {
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

    public int getName() {
        return name;
    }

    public void setName(int name) {
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
