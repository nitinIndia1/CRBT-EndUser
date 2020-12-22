package com.crbt.api.services.bean;

import java.io.Serializable;

public class FavoritesBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5993170113333302075L;
	private String subscriber_id;
	private String top_content_id;

	public FavoritesBean() {}

	
	
	public FavoritesBean(String subscriber_id, String top_content_id) {
		this.subscriber_id = subscriber_id;
		this.top_content_id = top_content_id;
	}



	public String getSubscriber_id() {
		return subscriber_id;
	}

	public void setSubscriber_id(String subscriber_id) {
		this.subscriber_id = subscriber_id;
	}

	public String getTop_content_id() {
		return top_content_id;
	}

	public void setTop_content_id(String top_content_id) {
		this.top_content_id = top_content_id;
	}
	
	
}
