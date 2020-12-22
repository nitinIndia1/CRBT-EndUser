package com.crbt.api.services.bean;

public class ChangeAlbumStatusBean {

	private Integer album_id;
	private String song_status;

	public ChangeAlbumStatusBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ChangeAlbumStatusBean(Integer album_id, String song_status) {
		super();
		this.album_id = album_id;
		this.song_status = song_status;
	}

	public Integer getAlbum_id() {
		return album_id;
	}

	public void setAlbum_id(Integer album_id) {
		this.album_id = album_id;
	}

	public String getSong_status() {
		return song_status;
	}

	public void setSong_status(String song_status) {
		this.song_status = song_status;
	}

	@Override
	public String toString() {
		return "ChangeAlbumStatusBean [album_id=" + album_id + ", song_status=" + song_status + "]";
	}

}
