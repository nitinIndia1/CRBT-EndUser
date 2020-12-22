package com.crbt.api.services.controller;

import java.net.URLEncoder;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.crbt.api.services.bean.ErrorMessage;
import com.crbt.api.services.bean.OtpResponse;
import com.crbt.api.services.bean.SmsTemplateBean;
import com.crbt.api.services.domain.SmsKeywordTemplate;
import com.crbt.api.services.domain.Subscriber;
import com.crbt.api.services.domain.Subscription;
import com.crbt.api.services.service.SmsService;
import com.crbt.api.services.service.SubscriberService;
import com.crbt.api.services.service.SubscriptionService;
import com.crbt.api.services.utils.GenerateRandom;

@RestController
public class SmsController {

	private static final Logger logger = LoggerFactory.getLogger( SmsController.class );
	
	@Autowired
	private RestTemplate template;
	private String SMSURL = "http://smsapi.24x7sms.com/api_2.0/SendSMS.aspx?APIKEY=y6LATSpSiCM&MobileNo=~MSISDN~&SenderID=ASHOKA&Message=~MESSAGE~&ServiceName=PROMOTIONAL_HIGH";
	private SmsService smsService;
	private SubscriberService subscriberService;
	private SubscriptionService subscriptionService;
	
	@Inject
	public SmsController( SubscriberService subscriberService, SubscriptionService subscriptionService, SmsService smsService ) {
		this.subscriberService = subscriberService;
		this.subscriptionService = subscriptionService;
		this.smsService = smsService;
	}
	
	@RequestMapping(value = "/send_sms/otp/{msisdn}", method = RequestMethod.POST  )
	public OtpResponse sendSmsForOtp( @PathVariable final String msisdn ){
		logger.info( "Received request to send sms to the msisdn: {} with message: {}", msisdn );
		OtpResponse response = new OtpResponse();
		Integer otp = GenerateRandom.getOtp();
		String resp = template.getForObject(SMSURL.replace("~MSISDN~", msisdn).replace("~MESSAGE~", "OTP for CRBT registration is "+ otp ), String.class );
		response.setOtp(otp+"");
		response.setResponse(resp);
		return response;
		
	}
	
	@RequestMapping( value = "/get_sms_template", method = RequestMethod.POST )
	public ResponseEntity<?> getSmsTemplateByKeyword( @RequestBody final SmsTemplateBean templateRequest ){
		logger.info( "Received request to get sms template for the credentials: {}", templateRequest );
		ErrorMessage error = null;
		if( templateRequest != null ) {
			Subscriber subscriber = subscriberService.getSubscriberByMsisdnOnly( templateRequest.getMsisdn() );
			if( subscriber != null ) {				
				Subscription subscription = subscriptionService.getActiveSubscriptionBySubscriber(subscriber);
				logger.info("Subscription found: {}", subscription);
				if( subscription != null ) {					
					SmsKeywordTemplate template = smsService.getSmsTemplateByKeyword( templateRequest.getKeyword() );
					if( template != null ) {
						SmsTemplateBean templateResponse = new SmsTemplateBean();
						String message = null;
						if( templateRequest.getLocale().equalsIgnoreCase("en") ) {
							String songName = subscription.getContentSongs().getSongsName().replaceAll("-", " ").replaceAll("_", " ");
							message = template.getSuccessReplyEn();
							message = message.replaceAll("<song_name>", songName );
							message = message.replaceAll("<Date>", subscription.getEndDate()+"" );
						}else {
							message = template.getSuccessReplyAr();
							message = message.replaceAll("<song_name>", subscription.getContentSongs().getSongsNameAr() );
							message = message.replaceAll("<Date>", subscription.getEndDate()+"" );
						}
						templateResponse.setMsisdn( templateRequest.getMsisdn() );
						templateResponse.setKeyword( templateRequest.getKeyword() );
						templateResponse.setLocale( templateRequest.getLocale() );
						try {
							if( templateRequest.getLocale().equalsIgnoreCase("en") )
								templateResponse.setMessage( URLEncoder.encode(message, "UTF-8").replace("+", "%20") );
							else
								templateResponse.setMessage( URLEncoder.encode(message, "UTF-8").replace("+", "%20") );
						}catch(Exception e) {
							e.printStackTrace();
						}
						logger.info("Encoded message: {}", templateResponse.getMessage());
						return new ResponseEntity<SmsTemplateBean>( templateResponse, HttpStatus.OK );
					}else {
						error = new ErrorMessage();
						error.setErrorField( "keyword" );
						error.setErrorCode( "404" );
						error.setErrorDescription( "No sms template found for the provied keyword." );
						return new ResponseEntity<ErrorMessage>( error, HttpStatus.OK );
					}					
				}else {
					error = new ErrorMessage();
					error.setErrorField( "msisdn" );
					error.setErrorCode( "404" );
					if( subscriber.getChangeLanguage().getId() == 2 )
						error.setErrorDescription( "عزيزي المُشترك، إشتراكك غير مفعل في خدمة مرحبا، للإشتراك أرسل رمز الرنة في رسالة نصية إلى الرقم 99128 أو إتصل على 128" );
					else
						error.setErrorDescription( "No active subscription found." );
					
					return new ResponseEntity<ErrorMessage>( error, HttpStatus.OK );
				}
								
			}else {
				/*Fetch no subscription only fetch message template*/
				SmsKeywordTemplate template = smsService.getSmsTemplateByKeyword( templateRequest.getKeyword() );
				if( template != null ) {
					SmsTemplateBean templateResponse = new SmsTemplateBean();
					String message = null;
					if( templateRequest.getLocale().equalsIgnoreCase("en") ) {
						message = template.getSuccessReplyEn();
					}else {
						message = template.getSuccessReplyAr();						
					}
					templateResponse.setMsisdn( templateRequest.getMsisdn() );
					templateResponse.setKeyword( templateRequest.getKeyword() );
					templateResponse.setLocale( templateRequest.getLocale() );
					templateResponse.setMessage( message );
					try {
						if( templateRequest.getLocale().equalsIgnoreCase("en") )
							templateResponse.setMessage( URLEncoder.encode(message, "UTF-8").replace("+", "%20") );
						else
							templateResponse.setMessage( URLEncoder.encode(message, "UTF-8").replace("+", "%20") );
					}catch(Exception e) {
						e.printStackTrace();
					}
					logger.info("Encoded message: {}", templateResponse.getMessage());
					return new ResponseEntity<SmsTemplateBean>( templateResponse, HttpStatus.OK );
				}else {
					error = new ErrorMessage();
					error.setErrorField( "keyword" );
					error.setErrorCode( "404" );
					error.setErrorDescription( "No sms template found for the provied keyword." );
					return new ResponseEntity<ErrorMessage>( error, HttpStatus.OK );
				}				
				/*error = new ErrorMessage();
				error.setErrorField( "msisdn" );
				error.setErrorCode( "404" );
				error.setErrorDescription( "No subscriber information found by the msisdn." );
				return new ResponseEntity<ErrorMessage>( error, HttpStatus.OK );*/
			}
			
		}else {
			error = new ErrorMessage();
			error.setErrorField( null );
			error.setErrorCode( "400" );
			error.setErrorDescription( "Bad request credentials." );
			return new ResponseEntity<ErrorMessage>( error, HttpStatus.OK );
		}		
		
	}
	
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
	   // Do any additional configuration here
	   return builder.build();
	}
}
