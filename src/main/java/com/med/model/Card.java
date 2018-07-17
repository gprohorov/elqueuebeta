package com.med.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by george on 3/31/18.
 */
@Document
public class Card {
    @Id
    private int id;
    private int procedureId;
    private String name;
    private int days;
    private boolean anytime;
    private List<Integer> closeAfter;      // these procedures must be cancelled AFTER this procedure
                                                // i.e. ultrasound after laser must be removed
    private List<Integer> activateAfter;  // these procedures must be activated AFTER this procedure
                                          // i.e massage after water-pulling. Because of gell.
    private List<Integer> mustBeDoneBefore; //  // these procedures must be done BEFORE this procedure

    public Card() {
    }

    public List<Integer> getMustBeDoneBefore() {
        return mustBeDoneBefore;
    }

    public void setMustBeDoneBefore(List<Integer> mustBeDoneBefore) {
        this.mustBeDoneBefore = mustBeDoneBefore;
    }

    public Card(int id, int procedureId, String name, int days, boolean anytime, List<Integer> closeAfter, List<Integer> activateAfter, List<Integer> mustBeDoneBefore) {
        this.id = id;
        this.procedureId = procedureId;
        this.name = name;
        this.days = days;
        this.anytime = anytime;
        this.closeAfter = closeAfter;
        this.activateAfter = activateAfter;
        this.mustBeDoneBefore = mustBeDoneBefore;
    }

    public List<Integer> getCloseAfter() {
        return closeAfter;
    }

    public void setCloseAfter(List<Integer> closeAfter) {
        this.closeAfter = closeAfter;
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

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getProcedureId() {
        return procedureId;
    }

    public void setProcedureId(int procedureId) {
        this.procedureId = procedureId;
    }

    public boolean isAnytime() {
        return anytime;
    }

    public void setAnytime(boolean anytime) {
        this.anytime = anytime;
    }


    public List<Integer> getActivateAfter() {
        return activateAfter;
    }

    public void setActivateAfter(List<Integer> openAfter) {
        this.activateAfter = openAfter;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", procedureId=" + procedureId +
                ", name='" + name + '\'' +
                ", days=" + days +
                ", anytime=" + anytime +
                ", closeAfter=" + closeAfter +
                ", activateAfter=" + activateAfter +
                ", mustBeDoneBefore=" + mustBeDoneBefore +
                '}';
    }
}
