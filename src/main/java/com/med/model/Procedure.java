package com.med.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private int number;
    private Card card;
    private ProcedureType procedureType;
    @Transient
    private int today;

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

    public Procedure(String name, int cabinet, int FOREIGN, int VIP, int BUSINESS, int ALL_INCLUSIVE, int SOCIAL, boolean zoned, int number, Card card) {
        this.name = name;
        this.cabinet = cabinet;
        this.FOREIGN = FOREIGN;
        this.VIP = VIP;
        this.BUSINESS = BUSINESS;
        this.ALL_INCLUSIVE = ALL_INCLUSIVE;
        this.SOCIAL = SOCIAL;
        this.zoned = zoned;
        this.number = number;
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

    public Procedure(String name, int cabinet, int FOREIGN, int VIP, int BUSINESS, int ALL_INCLUSIVE, int SOCIAL, boolean zoned, int number, Card card, ProcedureType procedureType) {
        this.name = name;
        this.cabinet = cabinet;
        this.FOREIGN = FOREIGN;
        this.VIP = VIP;
        this.BUSINESS = BUSINESS;
        this.ALL_INCLUSIVE = ALL_INCLUSIVE;
        this.SOCIAL = SOCIAL;
        this.zoned = zoned;
        this.number = number;
        this.card = card;
        this.procedureType = procedureType;
    }

    public ProcedureType getProcedureType() {
        return procedureType;
    }

    public void setProcedureType(ProcedureType procedureType) {
        this.procedureType = procedureType;
    }

    public int getToday() {
        return today;
    }

    public void setToday(int today) {
        this.today = today;
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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
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

    @Override
    public String toString() {
        return "Procedure{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cabinet=" + cabinet +
                ", FOREIGN=" + FOREIGN +
                ", VIP=" + VIP +
                ", BUSINESS=" + BUSINESS +
                ", ALL_INCLUSIVE=" + ALL_INCLUSIVE +
                ", SOCIAL=" + SOCIAL +
                ", zoned=" + zoned +
                ", number=" + number +
                ", card=" + card +
                ", procedureType=" + procedureType +
                ", today=" + today +
                '}';
    }
}
