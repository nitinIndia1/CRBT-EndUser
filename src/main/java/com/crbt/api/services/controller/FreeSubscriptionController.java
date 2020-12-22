package com.crbt.api.services.controller;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.crbt.api.services.bean.GiftData;
import com.crbt.api.services.bean.HssBean;
import com.crbt.api.services.bean.SmsTemplateBean;
import com.crbt.api.services.domain.ContentSongs;
import com.crbt.api.services.domain.Language;
import com.crbt.api.services.domain.Subscriber;
import com.crbt.api.services.domain.Subscription;
import com.crbt.api.services.service.ContentSongsService;
import com.crbt.api.services.service.LanguageMasterService;
import com.crbt.api.services.service.SendSMSService;
import com.crbt.api.services.service.SubscriberService;
import com.crbt.api.services.service.SubscriptionService;
import com.crbt.api.services.utils.ApplicationResponse;
import com.crbt.api.services.utils.CoreResponseHandler;
import com.crbt.api.services.utils.ResponseStatusEnum;
import com.crbt.api.services.utils.SuccessResponseBeanRefined;

@RestController
public class FreeSubscriptionController {

	private static final Logger logger = LoggerFactory
			.getLogger(FreeSubscriptionController.class);


	@Autowired
	private SubscriberService subscriberService;

	@Autowired
	private SubscriptionService subscriptionService;

	@Autowired
	private ContentSongsService contentSongsService;

	@Autowired
	private LanguageMasterService languageService;

	@Autowired
	private SendSMSService sendSmsService;

	@Value("${service.get.smstemplate}")
	private String smsTemplateUrl;

	@PostMapping(value="freesub/changesong",consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CoreResponseHandler> freeSubscriptionChangeSong(@RequestBody final FreeSubscriptionData freeSubData){
		long l_time_start = System.currentTimeMillis();
		RestTemplate restTemplate = new RestTemplate();
		ContentSongs contentSongs =  contentSongsService.getSongsBySongId(freeSubData.getRbtID());
		if(contentSongs!=null) {
			Subscriber subscriber =  subscriberService.checkSubscriberByMsisdn(freeSubData.getMsisdn());

			if(subscriber != null) {
				Subscription subscription = subscriptionService.getActiveSubscriptionBySubscriber(subscriber);
				if(subscription.getStatus().equals("D")){
					
					//sendSms(freeSubData.getMsisdn(),"FREE_SONG_C");
					
					SmsTemplateBean smsRequest = new SmsTemplateBean();
					smsRequest.setMsisdn(freeSubData.getMsisdn());
					smsRequest.setLocale("ar");
					smsRequest.setKeyword("FREE_SONG_C");
					smsRequest.setMessage(null);
					String messageToSend = getSMSTemplateContent(smsRequest);
					logger.info("SENDING SMS For : KEYWORD: {} MESSAGE: {} TO: {}", new Object[] { smsRequest.getKeyword(), messageToSend, freeSubData.getMsisdn()});
					int status = this.sendSmsService.sendSMSRequest(freeSubData.getMsisdn(), messageToSend, smsRequest.getLocale());
					if (status == 0) {
						logger.info("SMS has been sent successfully to msisdn {} with response code: {}", freeSubData.getMsisdn(), Integer.valueOf(status));
					} else {
						logger.info("Failed to send SMS to msisdn {} with response code: {}", freeSubData.getMsisdn(), Integer.valueOf(status));
					} 
					
					subscription.setContentSongs(contentSongs);
					subscription.setRemarks("Free Subscription - Song Change from "+freeSubData.getMode()+" Tone Id "+freeSubData.getRbtID());
					subscription.setStatus("D");
					subscriptionService.saveUpdateSongStatus(subscription);
					
					HssBean hssBean = new HssBean();
					hssBean.setMsisdn(freeSubData.getMsisdn());
					hssBean.setSubscribe(true);
					String HSS_URI = "http://10.111.222.108:4545/crbt/api/hss/provision";
					HttpHeaders headers = new HttpHeaders();
					headers.setContentType( MediaType.APPLICATION_JSON );
					HttpEntity<String> entity = new HttpEntity( hssBean, headers );
					try {
						String resString =  restTemplate.exchange( HSS_URI, HttpMethod.POST, entity, String.class ).getBody();
						System.out.println("HSS Response "+ resString);
					}catch( Exception e ) {
						logger.error( "ERROR: Failed to connect hss provisioning service!" );
						e.printStackTrace();

					}		

				}


			}
		}

		long l_time_end = System.currentTimeMillis();
		long l_diff = l_time_end-l_time_start;
		return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, 
				ApplicationResponse.SUCCESSFUL, "subscription updated.",l_diff+" ms") ,HttpStatus.OK);

	}

	@PostMapping(value="freesub/unsub",consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CoreResponseHandler> freeSubscriptionUnsub(@RequestBody final FreeSubscriptionData freeSubData){
		long l_time_start = System.currentTimeMillis();
		RestTemplate restTemplate = new RestTemplate();
		Subscriber subscriber =  subscriberService.checkSubscriberByMsisdn(freeSubData.getMsisdn());

		if(subscriber != null) {
			Subscription subscription = subscriptionService.getActiveSubscriptionBySubscriber(subscriber);
			if(subscription!=null && subscription.getStatus().equals("D")){
				//sendSms(freeSubData.getMsisdn(),"FREE_UNSUB");
				
				SmsTemplateBean smsRequest = new SmsTemplateBean();
				smsRequest.setMsisdn(freeSubData.getMsisdn());
				smsRequest.setLocale("ar");
				smsRequest.setKeyword("FREE_UNSUB");
				smsRequest.setMessage(null);
				String messageToSend = getSMSTemplateContent(smsRequest);
				logger.info("SENDING SMS For : KEYWORD: {} MESSAGE: {} TO: {}", new Object[] { smsRequest.getKeyword(), messageToSend, freeSubData.getMsisdn()});
				int status = this.sendSmsService.sendSMSRequest(freeSubData.getMsisdn(), messageToSend, smsRequest.getLocale());
				if (status == 0) {
					logger.info("SMS has been sent successfully to msisdn {} with response code: {}", freeSubData.getMsisdn(), Integer.valueOf(status));
				} else {
					logger.info("Failed to send SMS to msisdn {} with response code: {}", freeSubData.getMsisdn(), Integer.valueOf(status));
				} 
				
				subscription.setRemarks("Free Subscription - UNSUB Request from "+freeSubData.getMode()+" Tone Id "+freeSubData.getRbtID());
				subscription.setStatus("N");
				subscriptionService.saveUpdateSongStatus(subscription);
				
				HssBean hssBean = new HssBean();
				hssBean.setMsisdn(freeSubData.getMsisdn());
				hssBean.setSubscribe(false);
				String HSS_URI = "http://10.111.222.108:4545/crbt/api/hss/provision";
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType( MediaType.APPLICATION_JSON );
				HttpEntity<String> entity = new HttpEntity( hssBean, headers );
				try {
					String resString =  restTemplate.exchange( HSS_URI, HttpMethod.POST, entity, String.class ).getBody();
					System.out.println("HSS Response "+ resString);
				}catch( Exception e ) {
					logger.error( "ERROR: Failed to connect hss provisioning service!" );
					e.printStackTrace();
				}		
			}
		}

		long l_time_end = System.currentTimeMillis();
		long l_diff = l_time_end-l_time_start;
		return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, 
				ApplicationResponse.SUCCESSFUL, "subscription updated.",l_diff+" ms") ,HttpStatus.OK);
	}

	private void sendSms(String msisdn, String keyword) {
		SmsTemplateBean smsRequest = new SmsTemplateBean();
		smsRequest.setMsisdn(msisdn);
		smsRequest.setLocale("ar");
		smsRequest.setKeyword(keyword);
		smsRequest.setMessage(null);
		String messageToSend = getSMSTemplateContent(smsRequest);
		logger.info("SENDING SMS For : KEYWORD: {} MESSAGE: {} TO: {}", new Object[] { smsRequest.getKeyword(), messageToSend, msisdn});
		int status = this.sendSmsService.sendSMSRequest(msisdn, messageToSend, smsRequest.getLocale());
		if (status == 0) {
			logger.info("SMS has been sent successfully to msisdn {} with response code: {}", msisdn, Integer.valueOf(status));
		} else {
			logger.info("Failed to send SMS to msisdn {} with response code: {}", msisdn, Integer.valueOf(status));
		} 
	}

	private String getDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}

	private Date addDays(Date date, int days) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance();
		c.setTime(date); 
		c.add(Calendar.DATE, days); // Adding 7 days
		return c.getTime();
	}
	private String getSMSTemplateContent(SmsTemplateBean sms) {
		RestTemplate smsClient = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		HttpEntity<SmsTemplateBean> entity = new HttpEntity(sms, (MultiValueMap)headers);
		try {
			return ((SmsTemplateBean)smsClient.exchange(this.smsTemplateUrl, HttpMethod.POST, entity, SmsTemplateBean.class, new Object[0]).getBody()).getMessage();
		} catch (Exception e) {
			logger.error("Failed to connect end user service! Description: {}", e.getMessage());
			e.printStackTrace();
			return null;
		} 
	}
}
