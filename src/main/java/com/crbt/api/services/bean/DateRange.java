package com.crbt.api.services.bean;

public class DateRange {
	
	private String start_date;
	private String end_date;
	
	public DateRange() {
		// TODO Auto-generated constructor stub
	}

	public DateRange(String start_date, String end_date) {
		super();
		this.start_date = start_date;
		this.end_date = end_date;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	

}
