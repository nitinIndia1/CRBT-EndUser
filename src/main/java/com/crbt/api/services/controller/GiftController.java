package com.crbt.api.services.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.crbt.api.services.bean.DebitBalance;
import com.crbt.api.services.bean.GiftData;
import com.crbt.api.services.bean.HssBean;
import com.crbt.api.services.domain.ContentSongs;
import com.crbt.api.services.domain.Language;
import com.crbt.api.services.domain.Subscriber;
import com.crbt.api.services.domain.Subscription;
import com.crbt.api.services.service.ContentSongsService;
import com.crbt.api.services.service.LanguageMasterService;
import com.crbt.api.services.service.SubscriberService;
import com.crbt.api.services.service.SubscriptionService;
import com.crbt.api.services.utils.ApplicationResponse;
import com.crbt.api.services.utils.CoreResponseHandler;
import com.crbt.api.services.utils.ResponseStatusEnum;
import com.crbt.api.services.utils.SuccessResponseBeanRefined;


@RestController
public class GiftController {

	private static final Logger logger = LoggerFactory
			.getLogger(GiftController.class);


	@Autowired
	private SubscriberService subscriberService;

	@Autowired
	private SubscriptionService subscriptionService;

	@Autowired
	private ContentSongsService contentSongsService;

	@Autowired
	private LanguageMasterService languageService;


	@PostMapping(value="gift",consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CoreResponseHandler> giftApi(@RequestBody final GiftData giftData){
		long l_time_start = System.currentTimeMillis();
		String endDate = "";
		ContentSongs contentSongs =  contentSongsService.getSongsBySongId(Integer.parseInt(giftData.getRbtID()));
		if(contentSongs!=null) {
			DebitBalance debitBalance = new DebitBalance();
			debitBalance.setAmount(1000L);
			debitBalance.setChargingAddress(giftData.getGifter());
			debitBalance.setDestinationAddress(giftData.getGifter());
			debitBalance.setOriginatingAddress(giftData.getGifter());
			debitBalance.setTransactionId("");


			RestTemplate restTemplate = new RestTemplate();
			String ROOT_URI = "http://10.111.222.106:8083/crbt/api/ocs/debitUserAccount";
			ResponseEntity<?> response = restTemplate
					.postForEntity(ROOT_URI, debitBalance,
							String.class);

			String billingResponse = (String) response.getBody();
			System.out.println("billingresponse :: "+billingResponse);


			if(billingResponse!=null && billingResponse.contains("<Amount>1000")) {
				Subscriber subscriber =  subscriberService.checkSubscriberByMsisdn(giftData.getGiftee());
				if(subscriber == null) {
					subscriber = new Subscriber();
					subscriber.setMsisdn(giftData.getGiftee());
					subscriber.setName("");
					subscriber.setPassword("");
					subscriber.setEmail("");
					subscriber.setLocation("");
					subscriber.setProfilePicPath("");
					subscriber.setIsAppUser("false");
					subscriber.setIsActive("false");
					subscriber.setDeviceType("");
					subscriber.setIsActive("true");
					subscriber.setCreatedOn(new Date());
					subscriber.setUpdatedOn(new Date());
					Language lang = languageService.listAllLanguage().get(1);
					subscriber.setChangeLanguage(lang);
					subscriber = subscriberService.save(subscriber);
				}

				Subscription subscription = subscriptionService.getActiveSubscriptionBySubscriber(subscriber);
				if(subscription!=null) {
					subscription.setContentSongs(contentSongs);
					subscription.setEndDate(addDays(subscription.getEndDate()));
					endDate = getDate(subscription.getEndDate());
					subscription.setSubscriptionFrom(giftData.getMode());
					subscription.setSubscriptionType("Y");
					subscription.setChargingStatus("success");
					subscription.setRetryCount(1);
					subscription.setRemarks(subscription.getRemarks()+", Tone Id "+giftData.getRbtID()+" Gifted by "+giftData.getGifter());
					subscriptionService.saveUpdateSongStatus(subscription);

				}else {
					subscription = subscriptionService.getSubscriptionByIdAndStatus(subscriber.getId(), "F");
					if(subscription!=null) {
						subscription.setContentSongs(contentSongs);
						subscription.setEndDate(addDays(subscription.getEndDate()));
						endDate = getDate(subscription.getEndDate());
						subscription.setSubscriptionFrom(giftData.getMode());
						subscription.setSubscriptionType("Y");
						subscription.setChargingStatus("success");
						subscription.setRetryCount(1);
						subscription.setRemarks(subscription.getRemarks()+", Tone Id "+giftData.getRbtID()+" Gifted by "+giftData.getGifter());
						subscription.setStatus("Y");
						subscriptionService.saveUpdateSongStatus(subscription);
					}else {
						subscription = new Subscription();
						subscription.setSubscriber(subscriber);
						subscription.setContentSongs(contentSongs);
						subscription.setStartDate(new Date());
						subscription.setEndDate(addDays(new Date()));
						endDate = getDate(subscription.getEndDate());
						subscription.setSubscriptionFrom(giftData.getMode());
						subscription.setSubscriptionType("Y");
						subscription.setChargingStatus("success");
						subscription.setRetryCount(1);
						subscription.setLastUpdatedOn(new Date());
						subscription.setStatus("Y");
						subscription.setRemarks(" Tone Id "+giftData.getRbtID()+" Gifted by "+giftData.getGifter());
						subscriptionService.saveUpdateSongStatus(subscription);
					}
				}
				
				HssBean hssBean = new HssBean();
				hssBean.setMsisdn(giftData.getGiftee());
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
				
				
				String data;
				try {
					data = "عزيزي المشترك، تم إشتراكك في خدمة \"مرحبا\" وسينتهي إشتراكك بتاريخ "+getDate(subscription.getEndDate())+" ، سيتم تجديد إشتراكك تلقائياً بعد أسبوع، لإلغاء الاشتراك اتصل على 128 أو أرسل 0 أو كلمة إلغاء إلى 99128. \r\n" + 
							"لمعرفة المزيد والجديد عن الخدمة أدخل على صفحة فيسبوك الخاصة بها\r\n" + 
							"  https://www.facebook.com/MarhabaRBT";
					//data = data.replace("+","%20");
					Map<String, String> vars = new HashMap<>();
					vars.put("msisdn", giftData.getGiftee());
					vars.put("text", data);
					restTemplate.getForObject("http://10.111.222.107:8080/crbt/smpp/api/send-sms?msisdn={msisdn}&from=99128&text={text}&configId=1&locale=ar&rid=1253623", 
					    String.class, vars);
				} catch (Exception e) {
					e.printStackTrace();
				}

				long l_time_end = System.currentTimeMillis();
				long l_diff = l_time_end-l_time_start;
				return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, 
						ApplicationResponse.SUCCESSFUL, "subscription updated.",l_diff+" ms") ,HttpStatus.OK);

			}else {
				long l_time_end = System.currentTimeMillis();
				long l_diff = l_time_end-l_time_start;
				String data;
				try {
					data = "عزيزي المشترك ، رصيدك غير كاف لإهداء النغمة. يرجى إعادة التعبئة وإعادة المحاولة.";
					//data = data.replace("+","%20");
					Map<String, String> vars = new HashMap<>();
					vars.put("msisdn", giftData.getGifter());
					vars.put("text", data);
					restTemplate.getForObject("http://10.111.222.107:8080/crbt/smpp/api/send-sms?msisdn={msisdn}&from=99128&text={text}&configId=1&locale=ar&rid=1253623", 
					    String.class, vars);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.BAD_REQUEST, ResponseStatusEnum.FAILED, 
						ApplicationResponse.Failed," > error occurred, billing response : "+billingResponse,l_diff+" ms"),HttpStatus.BAD_REQUEST);
			}
		}else {
			long l_time_end = System.currentTimeMillis();
			long l_diff = l_time_end-l_time_start;
			return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.NOT_FOUND, ResponseStatusEnum.FAILED, 
					ApplicationResponse.Failed," Invalid RBT ID : ",l_diff+" ms"),HttpStatus.NOT_FOUND);
		}



	}


	private String getDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}

	private Date addDays(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance();
		c.setTime(date); 
		c.add(Calendar.DATE, 7); // Adding 7 days
		return c.getTime();
	}



}
