package com.crbt.api.services.bean;

public class LoginWithOtp {
	
	private String msisdn;
	private String generated_otp;
	
	public LoginWithOtp(){
		
	}

	public LoginWithOtp(String msisdn, String generated_otp) {
		super();
		this.msisdn = msisdn;
		this.generated_otp = generated_otp;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getGenerated_otp() {
		return generated_otp;
	}

	public void setGenerated_otp(String generated_otp) {
		this.generated_otp = generated_otp;
	}

	@Override
	public String toString() {
		return "LoginWithOtp [msisdn=" + msisdn + ", generated_otp=" + generated_otp + "]";
	}
	
	

}
