package com.crbt.api.services.bean;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class SearchSongBean {

	@NotEmpty @NotNull
	private String songs_name;
	
	public SearchSongBean() {
		// TODO Auto-generated constructor stub
	}

	public SearchSongBean(String songs_name) {
		super();
		this.songs_name = songs_name;
	}

	public String getSongs_name() {
		return songs_name;
	}

	public void setSongs_name(String songs_name) {
		this.songs_name = songs_name;
	}
	
}
