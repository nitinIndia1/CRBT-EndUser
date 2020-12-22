package com.crbt.api.services.bean;

import org.springframework.web.multipart.MultipartFile;

public class SongRequestBean {

	private Integer album_id;
	private MultipartFile songs;

	public SongRequestBean() {
		// TODO Auto-generated constructor stub
	}

	public SongRequestBean(Integer album_id, MultipartFile songs) {
		super();
		this.album_id = album_id;
		this.songs = songs;
	}

	public Integer getAlbum_id() {
		return album_id;
	}

	public void setAlbum_id(Integer album_id) {
		this.album_id = album_id;
	}

	public MultipartFile getSongs() {
		return songs;
	}

	public void setSongs(MultipartFile songs) {
		this.songs = songs;
	}

	@Override
	public String toString() {
		return "SongRequestBean [album_id=" + album_id + ", songs=" + songs + "]";
	}

}
