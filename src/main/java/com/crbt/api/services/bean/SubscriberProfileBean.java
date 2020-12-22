package com.crbt.api.services.bean;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

public class SubscriberProfileBean {
	
	@NotEmpty @NotNull
	private String msisdn;
	@NotNull @NotEmpty
    private MultipartFile file;
	
	
	public SubscriberProfileBean() {
		// TODO Auto-generated constructor stub
	}
	
	
    public SubscriberProfileBean(String msisdn, MultipartFile file) {
		super();
		this.msisdn = msisdn;
		this.file = file;
	}


	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
	

}
