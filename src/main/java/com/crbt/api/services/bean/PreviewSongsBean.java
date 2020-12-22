package com.crbt.api.services.bean;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class PreviewSongsBean {

	@NotBlank
	@NotNull
	private Integer song_id;

	public PreviewSongsBean() {
		// TODO Auto-generated constructor stub
	}

	public PreviewSongsBean(Integer song_id) {
		super();
		this.song_id = song_id;
	}

	public Integer getSong_id() {
		return song_id;
	}

	public void setSong_id(Integer song_id) {
		this.song_id = song_id;
	}

	@Override
	public String toString() {
		return "PreviewSongsBean [song_id=" + song_id + "]";
	}

}
