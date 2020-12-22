package com.crbt.api.services.service;

public interface SendSMSService {

	public int sendSMSRequest( String msisdn, String text, String locale );
}
