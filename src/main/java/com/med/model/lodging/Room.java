package com.med.model.lodging;

import java.util.List;

/**
 * Created by george on 26.08.18.
 */
public class Room {
    private int id;
    private  String name;
    private String desc;
    private List<Roomer> roomers;
    private int price;

    public Room() {
    }

    public Room(int id, String name, String desc, List<Roomer> roomers, int price) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.roomers = roomers;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<Roomer> getRoomers() {
        return roomers;
    }

    public void setRoomers(List<Roomer> roomers) {
        this.roomers = roomers;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
