package com.crbt.api.services.bean;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

public class FileUpload {

	@NotNull @NotBlank
	private MultipartFile file;
	@NotNull @NotBlank @NotEmpty
	private String contentSongs;

	public FileUpload() {
		// TODO Auto-generated constructor stub
	}

	public FileUpload(MultipartFile file, String contentSongs) {
		super();
		this.file = file;
		this.contentSongs = contentSongs;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String getContentSongs() {
		return contentSongs;
	}

	public void setContentSongs(String contentSongs) {
		this.contentSongs = contentSongs;
	}

	@Override
	public String toString() {
		return "FileUpload [file=" + file + ", contentSongs=" + contentSongs + "]";
	}

}
