package com.med.model;

/**
 * Created by george on 22.05.18.
 */
public class HotelPlace {
    private int id;
    private  int name;
    private Chamber chamber;
    private int price;
    private boolean vacant;
    private Patient patient;

    public boolean isVacant() {
        return vacant;
    }

    public void setVacant(boolean vacant) {
        this.vacant = vacant;
    }

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
