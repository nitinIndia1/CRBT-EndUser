package com.crbt.api.services.bean;

public class LoginWithPassword {
	
	private String msisdn;
	private String password;
	
	public LoginWithPassword() {
		// TODO Auto-generated constructor stub
	}

	public LoginWithPassword(String msisdn, String password) {
		super();
		this.msisdn = msisdn;
		this.password = password;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "LoginWithPassword [msisdn=" + msisdn + ", password=" + password + "]";
	}
	
	

}
