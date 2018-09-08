package com.med.model.statistics.dto.procedure;

/**
 * Created by george on 27.08.18.
 */
public class ProcedureReceipt {

    String name;
    int price;
    int zones;
    long sum;

    public ProcedureReceipt() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
