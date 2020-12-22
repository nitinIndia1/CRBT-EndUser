package com.crbt.api.services.bean;

public class SongStatusBean {

	private Integer song_id;
	private String song_status;

	public SongStatusBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SongStatusBean(Integer song_id, String song_status) {
		super();
		this.song_id = song_id;
		this.song_status = song_status;
	}

	public Integer getSong_id() {
		return song_id;
	}

	public void setSong_id(Integer song_id) {
		this.song_id = song_id;
	}

	public String getSong_status() {
		return song_status;
	}

	public void setSong_status(String song_status) {
		this.song_status = song_status;
	}

	@Override
	public String toString() {
		return "SongStatusBean [song_id=" + song_id + ", song_status=" + song_status + "]";
	}

}
