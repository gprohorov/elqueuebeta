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
    private String notes;
    private int area;
    private int price;
    private int foreigner;
    private int vip;
    private int business;
    private int allInclusive;
    private int social;

    private Card card;
    private int assigned; // diagnost assigns the amount
    private int done;
    private boolean executed = false;

    public Procedure() {
    }

    public Procedure(int id, String name, int cabinet, String notes
            , int area, int price, int foreigner, int vip, int business
            , int allInclusive, int social, Card card, int assigned, int done
            , boolean executed) {
        this.id = id;
        this.name = name;
        this.cabinet = cabinet;
        this.notes = notes;
        this.area = area;
        this.price = price;
        this.foreigner = foreigner;
        this.vip = vip;
        this.business = business;
        this.allInclusive = allInclusive;
        this.social = social;
        this.card = card;
        this.assigned = assigned;
        this.done = done;
        this.executed = executed;
    }

    public Procedure(int id, String name, int cabinet, String notes, int area, int price, Card card, int assigned, int done, boolean executed) {
        this.id = id;
        this.name = name;
        this.cabinet = cabinet;
        this.notes = notes;
        this.area = area;
        this.price = price;
        this.card = card;
        this.assigned = assigned;
        this.done = done;
        this.executed = executed;
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
    public int hashCode() {
        return this.getId();
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * <p>
     * The {@code equals} method implements an equivalence relation
     * on non-null object references:
     * <ul>
     * <li>It is <i>reflexive</i>: for any non-null reference value
     * {@code x}, {@code x.equals(x)} should return
     * {@code true}.
     * <li>It is <i>symmetric</i>: for any non-null reference values
     * {@code x} and {@code y}, {@code x.equals(y)}
     * should return {@code true} if and only if
     * {@code y.equals(x)} returns {@code true}.
     * <li>It is <i>transitive</i>: for any non-null reference values
     * {@code x}, {@code y}, and {@code z}, if
     * {@code x.equals(y)} returns {@code true} and
     * {@code y.equals(z)} returns {@code true}, then
     * {@code x.equals(z)} should return {@code true}.
     * <li>It is <i>consistent</i>: for any non-null reference values
     * {@code x} and {@code y}, multiple invocations of
     * {@code x.equals(y)} consistently return {@code true}
     * or consistently return {@code false}, provided no
     * information used in {@code equals} comparisons on the
     * objects is modified.
     * <li>For any non-null reference value {@code x},
     * {@code x.equals(null)} should return {@code false}.
     * </ul>
     * <p>
     * The {@code equals} method for class {@code Object} implements
     * the most discriminating possible equivalence relation on objects;
     * that is, for any non-null reference values {@code x} and
     * {@code y}, this method returns {@code true} if and only
     * if {@code x} and {@code y} refer to the same object
     * ({@code x == y} has the value {@code true}).
     * <p>
     * Note that it is generally necessary to override the {@code hashCode}
     * method whenever this method is overridden, so as to maintain the
     * general contract for the {@code hashCode} method, which states
     * that equal objects must have equal hash codes.
     *
     * &param obj the reference object with which to compare.
     * @return {@code true} if this object is the same as the obj
     * argument; {@code false} otherwise.
     * @see #hashCode()
     * @see HashMap
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Procedure procedure = (Procedure) o;

        return this.getId() == procedure.getId();
    }

}
