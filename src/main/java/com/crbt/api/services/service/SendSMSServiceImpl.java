package com.crbt.api.services.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SendSMSServiceImpl implements SendSMSService{

	private static final Logger logger = LoggerFactory.getLogger( SendSMSServiceImpl.class );
	
	@Value( "${service.sms.url}" )
	private String sendSMSApi;
	@Value( "${sms.senderId}" )
	private String senderId;
	private final String USER_AGENT = "Mozilla/5.0";
	
	public int sendSMSRequest( String msisdn, String text, String locale ) {
		String sendSMSUrl = sendSMSApi;
		int status = 0;
		try {
			sendSMSUrl = sendSMSUrl.replace("<MSISDN>", msisdn);
			sendSMSUrl = sendSMSUrl.replace("<FROM>", senderId);
			sendSMSUrl = sendSMSUrl.replace("<TEXT>", text);
			sendSMSUrl = sendSMSUrl.replace("<LOCALE>", locale);
			
			
			logger.info("SMS Send Service:");
			logger.info("--------------------------------------------");
			logger.info("MSISDN: {}", msisdn);
			logger.info("FROM: {}", senderId);
			logger.info("LOCALE: {}", locale);
			logger.info("MESSAGE: " + text);
			logger.info("--------------------------------------------");
			
			URL url = new URL(sendSMSUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", USER_AGENT);
			int responseCode = con.getResponseCode();
			logger.info("SMS Send Response Code :: " + responseCode);
			logger.info("SMS Response Message :: " + con.getResponseMessage());
			if (responseCode == HttpURLConnection.HTTP_OK) { // success
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				// print result
				logger.info("SMS Response Body :: {}", response.toString());
			} else {
				logger.info("GET request not worked");
			}			
			
		}catch( Exception e ) {
			e.printStackTrace();
		}
		return status;
	}
	
	/*@Override
	public int sendSMSRequest( String msisdn, String text, String locale ) {		
		RestTemplate smsClient = new RestTemplate();
		sendSMSUrl = sendSMSUrl.replace("<MSISDN>", msisdn);
		sendSMSUrl = sendSMSUrl.replace("<FROM>", senderId);
		int status = 0;
		try {
			sendSMSUrl = sendSMSUrl.replace("<TEXT>", text);
			sendSMSUrl = sendSMSUrl.replace("<LOCALE>", locale);
			HttpHeaders headers = new HttpHeaders();
			HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);	

			status = smsClient.exchange(sendSMSUrl, HttpMethod.GET, entity, String.class).getStatusCode().value();
		}catch( Exception e ) {
			e.printStackTrace();
		}
		return status;
		//return 200;
	}*/

}
