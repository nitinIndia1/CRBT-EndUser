package com.crbt.api.services.bean;

import java.io.Serializable;

public class SendEmailBean implements Serializable{

	private static final long serialVersionUID = -191513256383922271L;

	private String name;
	private String mobile;
	private String email;
	private String queryType;
	private String subject;
	private String body;
	
	public SendEmailBean() {}

	public SendEmailBean(String name, String mobile, String email, String queryType, String subject, String body) {
		super();
		this.name = name;
		this.mobile = mobile;
		this.email = email;
		this.queryType = queryType;
		this.subject = subject;
		this.body = body;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "SendEmailBean [name=" + name + ", mobile=" + mobile + ", email=" + email + ", queryType=" + queryType
				+ ", subject=" + subject + ", body=" + body + "]";
	}	
	
}
