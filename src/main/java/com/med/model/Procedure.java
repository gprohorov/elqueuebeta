package com.med.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;

/**
 * Created by george on 3/9/18.
 */
@Document(collection = "procedure" )
public class Procedure {
    @Id
    private int id;
    private String name;
    private int cabinet;
    private int price;
    private int foreigner;
    private int vip;
    private int business;
    private int allInclusive;
    private int social;
    private boolean zoned;


    private int area;
    private Card card;

    private boolean executed = false;

    public Procedure() {
    }

    public Procedure(int id, String name, int cabinet, int price, int foreigner, int vip, int business, int allInclusive, int social, boolean zoned, int area, Card card, boolean executed) {
        this.id = id;
        this.name = name;
        this.cabinet = cabinet;
        this.price = price;
        this.foreigner = foreigner;
        this.vip = vip;
        this.business = business;
        this.allInclusive = allInclusive;
        this.social = social;
        this.zoned = zoned;
        this.area = area;
        this.card = card;
        this.executed = executed;
    }

    public Procedure(String name, int cabinet, int price, int foreigner, int vip, int business, int allInclusive, int social, boolean zoned, int area, Card card, boolean executed) {
        this.name = name;
        this.cabinet = cabinet;
        this.price = price;
        this.foreigner = foreigner;
        this.vip = vip;
        this.business = business;
        this.allInclusive = allInclusive;
        this.social = social;
        this.zoned = zoned;
        this.area = area;
        this.card = card;
        this.executed = executed;
    }

    public Procedure(String name, int cabinet, int price, boolean zoned) {
        this.name = name;
        this.cabinet = cabinet;
        this.price = price;
        this.zoned = zoned;
    }

    public Procedure(int id, String name, int price) {
        this.id = id;
        this.name = name;
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

    public int getCabinet() {
        return cabinet;
    }

    public void setCabinet(int cabinet) {
        this.cabinet = cabinet;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getForeigner() {
        return foreigner;
    }

    public void setForeigner(int foreigner) {
        this.foreigner = foreigner;
    }

    public int getVip() {
        return vip;
    }

    public void setVip(int vip) {
        this.vip = vip;
    }

    public int getBusiness() {
        return business;
    }

    public void setBusiness(int business) {
        this.business = business;
    }

    public int getAllInclusive() {
        return allInclusive;
    }

    public void setAllInclusive(int allInclusive) {
        this.allInclusive = allInclusive;
    }

    public int getSocial() {
        return social;
    }

    public void setSocial(int social) {
        this.social = social;
    }

    public boolean isZoned() {
        return zoned;
    }

    public void setZoned(boolean zoned) {
        this.zoned = zoned;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public boolean isExecuted() {
        return executed;
    }

    public void setExecuted(boolean executed) {
        this.executed = executed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Procedure procedure = (Procedure) o;

        return getId() == procedure.getId();
    }

    @Override
    public int hashCode() {
        return getId();
    }

    /**
     * Returns a hash code value for the object. This method is
     * supported for the benefit of hash tables such as those provided by
     * {@link HashMap}.
     * <p>
     * The general contract of {@code hashCode} is:
     * <ul>
     * <li>Whenever it is invoked on the same object more than once during
     * an execution of a Java application, the {@code hashCode} method
     * must consistently return the same integer, provided no information
     * used in {@code equals} comparisons on the object is modified.
     * This integer need not remain consistent from one execution of an
     * application to another execution of the same application.
     * <li>If two objects are equal according to the {@code equals(Object)}
     * method, then calling the {@code hashCode} method on each of
     * the two objects must produce the same integer result.
     * <li>It is <em>not</em> required that if two objects are unequal
     * according to the {@link Object#equals(Object)}
     * method, then calling the {@code hashCode} method on each of the
     * two objects must produce distinct integer results.  However, the
     * programmer should be aware that producing distinct integer results
     * for unequal objects may improve the performance of hash tables.
     * </ul>
     * <p>
     * As much as is reasonably practical, the hashCode method defined by
     * class {@code Object} does return distinct integers for distinct
     * objects. (This is typically implemented by converting the internal
     * address of the object into an integer, but this implementation
     * technique is not required by the
     * Java&trade; programming language.)
     *
     * @return a hash code value for this object.
     * @see Object#equals(Object)
     * @see System#identityHashCode
     */



    @Override
    public String toString() {
        return "Procedure{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cabinet=" + cabinet +
                ", price=" + price +
                ", foreigner=" + foreigner +
                ", vip=" + vip +
                ", business=" + business +
                ", allInclusive=" + allInclusive +
                ", social=" + social +
                ", zoned=" + zoned +
                ", area=" + area +
                ", card=" + card +
                ", executed=" + executed +
                '}';
    }
}
