package com.crbt.api.services.bean;

import java.io.Serializable;

public class GiftData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5993170113625302075L;
	private String gifter;
	private String giftee;
	private String mode;
	private String rbtID;
	private String reminder;
	private int remdur;
	public GiftData() {}
	public GiftData(String gifter, String giftee, String mode, String rbtID, String reminder, int remdur) {
		this.gifter = gifter;
		this.giftee = giftee;
		this.mode = mode;
		this.rbtID = rbtID;
		this.reminder = reminder;
		this.remdur = remdur;
	}
	public String getGifter() {
		return gifter;
	}
	public void setGifter(String gifter) {
		this.gifter = gifter;
	}
	public String getGiftee() {
		return giftee;
	}
	public void setGiftee(String giftee) {
		this.giftee = giftee;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getRbtID() {
		return rbtID;
	}
	public void setRbtID(String rbtID) {
		this.rbtID = rbtID;
	}
	public String getReminder() {
		return reminder;
	}
	public void setReminder(String reminder) {
		this.reminder = reminder;
	}
	public int getRemdur() {
		return remdur;
	}
	public void setRemdur(int remdur) {
		this.remdur = remdur;
	}
	
	
}
