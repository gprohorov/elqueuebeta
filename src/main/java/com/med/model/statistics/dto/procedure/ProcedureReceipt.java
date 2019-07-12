package com.med.model.statistics.dto.procedure;

public class ProcedureReceipt {

    private String name;
    private int price;
    private int amount;
    private int zones;
    private long sum;

    public ProcedureReceipt() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
    	return amount;
	}

    public void setAmount(int amount) {
    	this.amount = amount;
	}

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getZones() {
        return zones;
    }

    public void setZones(int zones) {
        this.zones = zones;
    }

    public long getSum() {
        return sum;
    }

    public void setSum(long sum) {
        this.sum = sum;
    }
}