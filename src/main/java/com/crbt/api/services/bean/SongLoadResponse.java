package com.crbt.api.services.bean;

import java.util.List;

import com.crbt.api.services.domain.ContentSongs;

public class SongLoadResponse {

	private String albumName;
	private List<ContentSongs> songs;

	public SongLoadResponse() {
		// TODO Auto-generated constructor stub
	}

	public SongLoadResponse(String albumName, List<ContentSongs> songs) {
		super();
		this.albumName = albumName;
		this.songs = songs;
	}

	public String getAlbumName() {
		return albumName;
	}

	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}

	public List<ContentSongs> getSongs() {
		return songs;
	}

	public void setSongs(List<ContentSongs> songs) {
		this.songs = songs;
	}

	@Override
	public String toString() {
		return "SongLoadResponse [albumName=" + albumName + ", songs=" + songs + "]";
	}

}
