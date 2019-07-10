package com.med.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Settings {
    
	@Id
    private String id;
	private int tax;
	private int canteen;
    private String extractionItemId;
    private String salaryItemId;

    public Settings() {}

	public Settings(int tax, int canteen, String extractionItemId, String salaryItemId) {
		this.tax = tax;
		this.canteen = canteen;
		this.extractionItemId = extractionItemId;
		this.salaryItemId = salaryItemId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getTax() {
		return tax;
	}
	
	public int getCanteen() {
		return canteen;
	}

	public void setCanteen(int canteen) {
		this.canteen = canteen;
	}

	public void setTax(int tax) {
		this.tax = tax;
	}
	
	public String getSalaryItemId() {
		return salaryItemId;
	}
	
	public void setSalaryItemId(String salaryItemId) {
		this.salaryItemId = salaryItemId;
	}

	public String getExtractionItemId() {
		return extractionItemId;
	}

	public void setExtractionItemId(String extractionItemId) {
		this.extractionItemId = extractionItemId;
	}

	@Override
	public String toString() {
		return "Settings [id=" + id + ", tax=" + tax + ", canteen=" + canteen
				+ ", extractionItemId=" + extractionItemId + ", salaryItemId=" + salaryItemId + "]";
	}
}