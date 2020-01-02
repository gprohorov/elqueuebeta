package com.med.model.hotel;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.med.model.Category;

import java.util.Objects;

@Document
public class Chamber {
	
    @Id
    private int id;
    private String name;
    private Category category;
    private int floor;
    private String desc;
    private int beds;

    public Chamber() {}

    public Chamber(int id, String name, Category category, int floor, String desc, int beds) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.floor = floor;
        this.desc = desc;
        this.beds = beds;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getBeds() {
        return beds;
    }

    public void setBeds(int beds) {
        this.beds = beds;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    @Override
    public String toString() {
        return "Chamber{" + "id=" + id + ", name='" + name + '\'' + ", category=" + category +
            ", floor=" + floor + ", desc='" + desc + '\'' + ", beds=" + beds + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chamber chamber = (Chamber) o;
        return getId() == chamber.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
