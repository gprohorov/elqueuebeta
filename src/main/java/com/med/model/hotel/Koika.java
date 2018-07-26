package com.med.model.hotel;

import com.med.model.Patient;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.Nullable;

/**
 * Created by george on 22.05.18.
 */
@Document
public class Koika {

    @Id
    private int id;
    private String name;
    private Chamber chamber;
    private int price;
    @Nullable
    private Patient patient;

    public Koika() {
    }

    public Koika(int id, String name, Chamber chamber, int price, Patient patient) {
        this.id = id;
        this.name = name;
        this.chamber = chamber;
        this.price = price;
        this.patient = patient;
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

    public int compareTo(Koika two){
        int one = getChamber().getName().compareTo(two.getChamber().getName()) ;
        if (one == 0){
            return getName().compareTo(two.getName());
        }
        return one;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public String toString() {
        return "Koika{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", chamber=" + chamber.toString() +
                ", price=" + price +
                '}';
    }
}
