package com.crbt.api.services.bean;

import javax.validation.constraints.NotNull;

public class LanguageChangeBean {
	@NotNull
	private String msisdn;
	@NotNull
	private Integer changeLanguage;

	public LanguageChangeBean() {
	}

	public LanguageChangeBean(String msisdn, Integer changeLanguage) {
		super();
		this.msisdn = msisdn;
		this.changeLanguage = changeLanguage;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public Integer getChangeLanguage() {
		return changeLanguage;
	}

	public void setChangeLanguage(Integer changeLanguage) {
		this.changeLanguage = changeLanguage;
	}

}
