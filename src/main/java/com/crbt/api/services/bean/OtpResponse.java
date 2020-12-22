package com.crbt.api.services.bean;

public class OtpResponse {
	
	//private String msisdn;
	private String otp;
	private String response;
	
	public OtpResponse() {
		
	}

	public OtpResponse(String otp, String response) {
		super();
		this.otp = otp;
		this.response = response;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return "OtpResponse [otp=" + otp + ", response=" + response + "]";
	}

}
