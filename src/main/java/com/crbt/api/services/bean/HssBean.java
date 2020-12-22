package com.crbt.api.services.bean;

import java.io.Serializable;

public class HssBean implements Serializable{

	private static final long serialVersionUID = -3432707703446485062L;
	
	public String msisdn;
	public boolean subscribe;
	
	public HssBean() {}


	public HssBean(String msisdn, boolean subscribe) {
		super();
		this.msisdn = msisdn;
		this.subscribe = subscribe;
	}


	public String getMsisdn() {
		return msisdn;
	}


	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}


	public boolean isSubscribe() {
		return subscribe;
	}


	public void setSubscribe(boolean subscribe) {
		this.subscribe = subscribe;
	}


	@Override
	public String toString() {
		return "HssBean [msisdn=" + msisdn + ", subscribe=" + subscribe + "]";
	}	
	
	
}
