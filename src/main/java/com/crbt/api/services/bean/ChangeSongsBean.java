package com.crbt.api.services.bean;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class ChangeSongsBean {
	
	@NotNull
	private String msisdn;
	@NotNull @NotEmpty
	private Integer content_songs_id;
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public Integer getContent_songs_id() {
		return content_songs_id;
	}
	public void setContent_songs_id(Integer content_songs_id) {
		this.content_songs_id = content_songs_id;
	}
	
	
	
	

}
