package com.med.model.statistics.dto.accounting;

public class AvailableexecutedPart {
	
    private int payed;
    private int given;
    private int available;
    private int executed;
    private int percentage;

    public AvailableexecutedPart() {}

    public AvailableexecutedPart(int payed, int given, int available, int executed, int percentage) {
        this.payed = payed;
        this.given = given;
        this.available = available;
        this.executed = executed;
        this.percentage = percentage;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public int getExecuted() {
        return executed;
    }

    public void setExecuted(int executed) {
        this.executed = executed;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public int getPayed() {
        return payed;
    }

    public void setPayed(int payed) {
        this.payed = payed;
    }

    public int getGiven() {
        return given;
    }

    public void setGiven(int given) {
        this.given = given;
    }

	@Override
	public String toString() {
		return "AvailableexecutedPart [payed=" + payed + ", given=" + given + ", available=" + available + ", executed="
				+ executed + ", percentage=" + percentage + "]";
	}    
}