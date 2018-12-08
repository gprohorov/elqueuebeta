package com.med.model;

public class OutcomeTreeSum {
	
    private String category;
    private String name;
    private long sum;
    
    public OutcomeTreeSum() {}

    public OutcomeTreeSum(String category, String name, long sum) {
        this.category = category;
        this.name = name;
        this.sum = sum;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSum() {
        return sum;
    }

    public void setSum(long sum) {
        this.sum = sum;
    }
}