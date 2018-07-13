package com.med.model.hotel;

import com.med.model.Category;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by george on 22.05.18.
 */
@Document
public class Chamber {
    @Id
    private int id;
    private String name;
    private Category category;
    private String desc;
    private int beds;

    public Chamber() {
    }

    public Chamber(int id, String name, Category category, String desc, int beds) {
        this.id = id;
        this.name = name;
        this.category = category;
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

    @Override
    public String toString() {
        return "Chamber{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", desc='" + desc + '\'' +
                ", beds=" + beds +
                '}';
    }
}
