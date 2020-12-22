package com.crbt.api.services.bean;


import org.springframework.web.multipart.MultipartFile;

public class UploadFileBean {

	
	private MultipartFile file;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
}
