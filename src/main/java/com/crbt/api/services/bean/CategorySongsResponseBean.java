package com.crbt.api.services.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CategorySongsResponseBean {

	// private List<Category> Top20;
	private Integer song_id;
	private Integer category_id;
	private String song_name;
	private String contentPathLocation;
	@JsonIgnore
	private String category_name;
	private Integer id_index;	
	
	public CategorySongsResponseBean(){
		
	}
	
	public CategorySongsResponseBean(Integer song_id, Integer category_id, String song_name, String contentPathLocation, String category_name,
			Integer id_index) {
		super();
		this.song_id = song_id;
		this.category_id = category_id;
		this.song_name = song_name;
		this.contentPathLocation = contentPathLocation;
		this.category_name = category_name;
		this.id_index = id_index;
	}

	public Integer getSong_id() {
		return song_id;
	}

	public void setSong_id(Integer song_id) {
		this.song_id = song_id;
	}

	public Integer getCategory_id() {
		return category_id;
	}

	public void setCategory_id(Integer category_id) {
		this.category_id = category_id;
	}

	public String getSong_name() {
		return song_name;
	}

	public void setSong_name(String song_name) {
		this.song_name = song_name;
	}

	public String getContentPathLocation() {
		return contentPathLocation;
	}

	public void setContentPathLocation(String contentPathLocation) {
		this.contentPathLocation = contentPathLocation;
	}

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}

	public Integer getId_index() {
		return id_index;
	}

	public void setId_index(Integer id_index) {
		this.id_index = id_index;
	}

	@Override
	public String toString() {
		return "CategorySongsResponseBean [song_id=" + song_id + ", category_id=" + category_id + ", song_name="
				+ song_name + ", contentPathLocation=" + contentPathLocation + ", category_name=" + category_name
				+ ", id_index=" + id_index + "]";
	}	

}
