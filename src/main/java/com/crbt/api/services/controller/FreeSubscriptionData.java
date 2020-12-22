package com.crbt.api.services.controller;

public class FreeSubscriptionData {

	private int days;
	private String mode;
	private int rbtID;
	
	public FreeSubscriptionData() {}
	
	public FreeSubscriptionData(String msisdn, int days, String mode, int rbtID) {
		this.msisdn = msisdn;
		this.days = days;
		this.mode = mode;
		this.rbtID = rbtID;
	}
	private String msisdn;
	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public int getRbtID() {
		return rbtID;
	}

	public void setRbtID(int rbtID) {
		this.rbtID = rbtID;
	}

	
	
	
}
