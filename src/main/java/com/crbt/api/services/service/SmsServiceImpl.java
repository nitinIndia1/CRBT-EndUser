package com.crbt.api.services.service;

import java.util.Date;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;

import com.crbt.api.services.bean.OtpResponse;
import com.crbt.api.services.domain.SmsKeywordTemplate;
import com.crbt.api.services.repository.SmsKeywordTemplateRepo;
import com.crbt.api.services.utils.GenerateRandom;

@Service
public class SmsServiceImpl implements SmsService{
	
private static final Logger logger = LoggerFactory.getLogger( SmsServiceImpl.class );
	
	//@Autowired
	//private RestTemplate template;
	//private String SMSURL = "http://smsapi.24x7sms.com/api_2.0/SendSMS.aspx?APIKEY=y6LATSpSiCM&MobileNo=~MSISDN~&SenderID=ASHOKA&Message=~MESSAGE~&ServiceName=PROMOTIONAL_HIGH";
	private SmsKeywordTemplateRepo smsTemplateRepo;
	
	@Inject
	public SmsServiceImpl( SmsKeywordTemplateRepo smsTemplateRepo ) {
		this.smsTemplateRepo = smsTemplateRepo;
	}

	@Override
	public OtpResponse sendSmsForOtp(String msisdn) {		
		OtpResponse response = new OtpResponse();
		Integer otp1 = GenerateRandom.getOtp();
		//TEMPORARY BLOCK String resp = template.getForObject(SMSURL.replace("~MSISDN~", msisdn).replace("~MESSAGE~", "OTP for CRBT registration is "+ otp1 +"and is valid for 5 minutes."+""+"Generated on"+new Date()), String.class );
		String resp = "heheheh";
		response.setOtp(otp1+"");
		response.setResponse(resp);
		return response;
	}


	@Override
	public SmsKeywordTemplate getSmsTemplateByKeyword(String keyword) {
		logger.info( "Received request to get sms template for the keyword: {}", keyword );
		return smsTemplateRepo.getSmsTemplateByKeyword( keyword );
	}
	
	

}
