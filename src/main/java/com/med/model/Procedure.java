package com.med.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("FOREIGN")
    private int FOREIGN;
    @JsonProperty("VIP")
    private int VIP;
    @JsonProperty("BUSINESS")
    private int BUSINESS;
    @JsonProperty("ALL_INCLUSIVE")
    private int ALL_INCLUSIVE;
    @JsonProperty("SOCIAL")
    private int SOCIAL;
    private boolean zoned;
    private Card card;

    public Procedure() {
    }

    public Procedure(int id, String name, int cabinet, int FOREIGN, int VIP, int BUSINESS, int ALL_INCLUSIVE, int SOCIAL, boolean zoned, Card card) {
        this.id = id;
        this.name = name;
        this.cabinet = cabinet;
        this.FOREIGN = FOREIGN;
        this.VIP = VIP;
        this.BUSINESS = BUSINESS;
        this.ALL_INCLUSIVE = ALL_INCLUSIVE;
        this.SOCIAL = SOCIAL;
        this.zoned = zoned;
        this.card = card;
    }

    public Procedure(String name, int cabinet, int FOREIGN, int VIP, int BUSINESS, int ALL_INCLUSIVE, int SOCIAL, boolean zoned, Card card) {
        this.name = name;
        this.cabinet = cabinet;
        this.FOREIGN = FOREIGN;
        this.VIP = VIP;
        this.BUSINESS = BUSINESS;
        this.ALL_INCLUSIVE = ALL_INCLUSIVE;
        this.SOCIAL = SOCIAL;
        this.zoned = zoned;
        this.card = card;
    }

    public Procedure(String name, int cabinet, int FOREIGN, int VIP, int BUSINESS, int ALL_INCLUSIVE, int SOCIAL, boolean zoned) {
        this.name = name;
        this.cabinet = cabinet;
        this.FOREIGN = FOREIGN;
        this.VIP = VIP;
        this.BUSINESS = BUSINESS;
        this.ALL_INCLUSIVE = ALL_INCLUSIVE;
        this.SOCIAL = SOCIAL;
        this.zoned = zoned;
    }

    public Procedure(String name) {
        this.name = name;
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

    @JsonProperty("FOREIGN")
    public int getFOREIGN() {
        return FOREIGN;
    }

    @JsonProperty("FOREIGN")
    public void setFOREIGN(int FOREIGN) {
        this.FOREIGN = FOREIGN;
    }

    @JsonProperty("VIP")
    public int getVIP() {
        return VIP;
    }

    @JsonProperty("VIP")
    public void setVIP(int VIP) {
        this.VIP = VIP;
    }

    @JsonProperty("BUSINESS")
    public int getBUSINESS() {
        return BUSINESS;
    }

    @JsonProperty("BUSINESS")
    public void setBUSINESS(int BUSINESS) {
        this.BUSINESS = BUSINESS;
    }

    @JsonProperty("ALL_INCLUSIVE")
    public int getALL_INCLUSIVE() {
        return ALL_INCLUSIVE;
    }

    @JsonProperty("ALL_INCLUSIVE")
    public void setALL_INCLUSIVE(int ALL_INCLUSIVE) {
        this.ALL_INCLUSIVE = ALL_INCLUSIVE;
    }

    @JsonProperty("SOCIAL")
    public int getSOCIAL() {
        return SOCIAL;
    }

    @JsonProperty("SOCIAL")
    public void setSOCIAL(int SOCIAL) {
        this.SOCIAL = SOCIAL;
    }

    public boolean isZoned() {
        return zoned;
    }

    public void setZoned(boolean zoned) {
        this.zoned = zoned;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
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



}
