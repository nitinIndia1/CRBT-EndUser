package com.crbt.api.services.utils;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.http.HttpStatus;



public abstract class CoreResponseHandler implements Serializable {

	
	protected HttpStatus httpCode;
	
	protected ResponseStatusEnum status;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3790980019865748284L;
	public final static SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * @return Gives current timestamp
	 */	
	public  String getTimeStamp() {
		return formatter.format(new Date());
	}
	
	/**
	 * @return http status code from HttpStatus
	 */
	public abstract int getHttpCode();
	/**
	 * @return http status code from HttpStatus
	 */
	public abstract String getStatus();
}
