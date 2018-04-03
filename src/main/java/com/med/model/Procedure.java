package com.med.model;

/**
 * Created by george on 3/9/18.
 */
public class Procedure {

    private int id;
    private String name;
    private int cabinet;
    private String notes;
    private int area;
    private int price;
    private Card card;
    private int assigned;
    private int done;
    private boolean executed = false;

    public Procedure() {
    }

    public Procedure(int id, String name, int cabinet, String notes, int area, int price) {
        this.id = id;
        this.name = name;
        this.cabinet = cabinet;
        this.notes = notes;
        this.area = area;
        this.price = price;
        this.card = new Card();
    }

    public Procedure(int id, String name, int cabinet, int price) {
        this.id = id;
        this.name = name;
        this.cabinet = cabinet;
        this.notes = "";
        this.area = 1;
        this.price = price;
        this.card = new Card();
    }

    public Procedure(int id, String name, int cabinet, int price, boolean executed) {
        this.id = id;
        this.name = name;
        this.cabinet = cabinet;
        this.price = price;
        this.executed = executed;
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

    public int getCabinet() {
        return cabinet;
    }

    public void setCabinet(int cabinet) {
        this.cabinet = cabinet;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public int getAssigned() {
        return assigned;
    }

    public void setAssigned(int assigned) {
        this.assigned = assigned;
    }

    public int getDone() {
        return done;
    }

    public void setDone(int done) {
        this.done = done;
    }

    public boolean isExecuted() {
        return executed;
    }

    public void setExecuted(boolean executed) {
        this.executed = executed;
    }

    @Override
    public String toString() {
        return "Procedure{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cabinet=" + cabinet +
                ", notes='" + notes + '\'' +
                ", area=" + area +
                ", price=" + price +
                ", card=" + card +
                ", assigned=" + assigned +
                ", done=" + done +
                ", executed=" + executed +
                '}';
    }
}
