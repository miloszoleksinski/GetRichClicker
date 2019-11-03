package com.olas.GetRichClicker.user;

public class UpgradesModel 
{
	private String title;
	private String titleID;
	private long price;
	private float multiplier;
	private String operation;
	
	public String getTitle() {return title;}
	public void setTitle(String title) {this.title = title;}
	
	public String getTitleID() {return titleID;}
	public void setTitleID(String titleID) {this.titleID = titleID;}
	
	public long getPrice() {return price;}
	public void setPrice(long price) {this.price = price;}
	
	public float getMultiplier() {return multiplier;}
	public void setMultiplier(float multiplier) {this.multiplier = multiplier;}
	
	public String getOperation() {return operation;}
	public void setOperation(String operation) {this.operation = operation;}
}
