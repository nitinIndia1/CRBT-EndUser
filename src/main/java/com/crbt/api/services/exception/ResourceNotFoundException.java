package com.crbt.api.services.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -4167278957622291563L;
	private Integer resourceId;

	public ResourceNotFoundException(Integer resourceId, String message) {
		super(message);
		this.resourceId = resourceId;
	}

	
}
