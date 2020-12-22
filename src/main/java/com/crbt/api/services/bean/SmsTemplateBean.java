package com.crbt.api.services.bean;

import java.io.Serializable;

public class SmsTemplateBean implements Serializable{

	private static final long serialVersionUID = 5413276097151378914L;
	
	private String msisdn;
	private String locale;
	private String keyword;
	private String message;
	
	public SmsTemplateBean() {}

	public SmsTemplateBean(String msisdn, String locale, String keyword, String message) {
		super();
		this.msisdn = msisdn;
		this.locale = locale;
		this.keyword = keyword;
		this.message = message;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "SmsTemplateBean [msisdn=" + msisdn + ", locale=" + locale + ", keyword=" + keyword + ", message="
				+ message + "]";
	}
	
}
