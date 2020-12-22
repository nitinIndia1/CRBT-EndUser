package com.crbt.api.services.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="file")
public class FileStorageProperty {
	
	private String Window_SOURCE_FOLDER;

	public String getWindow_SOURCE_FOLDER() {
		return Window_SOURCE_FOLDER;
	}

	public void setWindow_SOURCE_FOLDER(String window_SOURCE_FOLDER) {
		Window_SOURCE_FOLDER = window_SOURCE_FOLDER;
	}
	
	
	

}
