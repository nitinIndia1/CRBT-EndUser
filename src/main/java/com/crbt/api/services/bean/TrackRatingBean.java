package com.crbt.api.services.bean;

import java.io.Serializable;

public class TrackRatingBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7819288619680276669L;
	/**
	 * 
	 */
	private String top_content_id;
	private String rating;
	private String remarks;
	public TrackRatingBean() {}
	public TrackRatingBean(String top_content_id, String rating, String remarks) {
		this.top_content_id = top_content_id;
		this.rating = rating;
		this.remarks = remarks;
	}
	public String getTop_content_id() {
		return top_content_id;
	}
	public void setTop_content_id(String top_content_id) {
		this.top_content_id = top_content_id;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

		
}
