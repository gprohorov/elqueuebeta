package com.med.model.statistics.dto.accounting;

/**
 * Created by george on 22.07.18.
 */
public class AvailableexecutedPart {
    private Long payed;
    private Long given;
    private Long available;
    private Long executed;
    private int percentage;

    public AvailableexecutedPart() {
    }

    public AvailableexecutedPart(Long payed, Long given, Long available, Long executed, int percentage) {
        this.payed = payed;
        this.given = given;
        this.available = available;
        this.executed = executed;
        this.percentage = percentage;
    }

    public Long getAvailable() {
        return available;
    }

    public void setAvailable(Long available) {
        this.available = available;
    }

    public Long getExecuted() {
        return executed;
    }

    public void setExecuted(Long executed) {
        this.executed = executed;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public Long getPayed() {
        return payed;
    }

    public void setPayed(Long payed) {
        this.payed = payed;
    }

    public Long getGiven() {
        return given;
    }

    public void setGiven(Long given) {
        this.given = given;
    }
}
