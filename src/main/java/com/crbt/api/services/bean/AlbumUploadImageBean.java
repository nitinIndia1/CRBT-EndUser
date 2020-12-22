package com.crbt.api.services.bean;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

public class AlbumUploadImageBean {

	@NotNull
	@NotEmpty
	private String contentAlbum;

	@NotNull
	@NotEmpty
	private MultipartFile file;

	public AlbumUploadImageBean() {
		// TODO Auto-generated constructor stub
	}

	public AlbumUploadImageBean(String contentAlbum, MultipartFile file) {
		super();
		this.contentAlbum = contentAlbum;
		this.file = file;
	}

	public String getContentAlbum() {
		return contentAlbum;
	}

	public void setContentAlbum(String contentAlbum) {
		this.contentAlbum = contentAlbum;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

}
