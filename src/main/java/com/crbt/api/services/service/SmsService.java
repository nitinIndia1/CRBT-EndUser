package com.crbt.api.services.service;


import com.crbt.api.services.bean.OtpResponse;
import com.crbt.api.services.domain.SmsKeywordTemplate;

public interface SmsService {

	
	OtpResponse sendSmsForOtp(String msisdn);
	SmsKeywordTemplate getSmsTemplateByKeyword( String keyword );
	
}
