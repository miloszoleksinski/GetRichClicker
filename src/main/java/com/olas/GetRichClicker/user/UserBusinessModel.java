package com.olas.GetRichClicker.user;

public class UserBusinessModel 
{
	private String username;
	private String businessTitle;
	private long boughtNumber;
	private double multiplier;
	private double cpsMultiplier;
	
	public String getUsername() {return username;}
	public void setUsername(String username) {this.username = username;}
	
	public String getBusinessTitle() {return businessTitle;}
	public void setBusinessTitle(String businessTitle) {this.businessTitle = businessTitle;}
	
	public long getBoughtNumber() {return boughtNumber;}
	public void setBoughtNumber(long boughtNumber) {this.boughtNumber = boughtNumber;}
	
	public double getMultiplier() {return multiplier;}
	public void setMultiplier(double multiplier) {this.multiplier = multiplier;}
	
	public double getCpsMultiplier() {return cpsMultiplier;}
	public void setCpsMultiplier(double cpsMultiplier) {this.cpsMultiplier = cpsMultiplier;}
}
