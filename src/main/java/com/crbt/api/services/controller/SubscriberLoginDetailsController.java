package com.crbt.api.services.controller;

import java.io.Console;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crbt.api.services.bean.ErrorMessage;
import com.crbt.api.services.bean.LoginWithOtp;
import com.crbt.api.services.bean.OtpResponse;
import com.crbt.api.services.domain.Subscriber;
import com.crbt.api.services.domain.SubscriberLoginDetails;
import com.crbt.api.services.exception.ResourceNotFoundException;
import com.crbt.api.services.service.SmsService;
import com.crbt.api.services.service.SubscriberLoginDetailsService;
import com.crbt.api.services.service.SubscriberService;
import com.crbt.api.services.validator.SubscriberLoginDetailsValidator;

@RestController
public class SubscriberLoginDetailsController {

	private static final Logger logger = LoggerFactory.getLogger(SubscriberLoginDetailsController.class);
	private final SubscriberLoginDetailsService loginService;
	private final SubscriberService subscriberService;
	private final SmsService smsService;
	private final SubscriberLoginDetailsValidator validator;

	@Inject
	public SubscriberLoginDetailsController(SubscriberLoginDetailsService loginService,
			SubscriberService subscriberService, SmsService smsService, SubscriberLoginDetailsValidator validator) {
		super();
		this.loginService = loginService;
		this.subscriberService = subscriberService;
		this.smsService = smsService;
		this.validator = validator;
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}

	@RequestMapping(value = "sublogin/create", method = RequestMethod.POST)
	public ResponseEntity<?> saveOrUpdateUserInfo(
			@Valid @RequestBody final SubscriberLoginDetails subscriberLoginDetails, Errors errors) {

		if (errors.hasErrors()) {
			logger.error("########### VALIDATION ERROR OCCURED: SubscriberLoginDetails Controller ##############");
			List<FieldError> fielderrors = errors.getFieldErrors();
			List<ErrorMessage> errorList = new ArrayList<ErrorMessage>();

			for (Iterator<FieldError> iterator = fielderrors.iterator(); iterator.hasNext();) {
				FieldError ferrors = (FieldError) iterator.next();
				errorList.add(new ErrorMessage(ferrors.getField(),
					HttpStatus.BAD_REQUEST.toString(), ferrors.getCode()));
			}

			logger.error(errorList.toString());

			return new ResponseEntity<List<ErrorMessage>>(errorList, HttpStatus.BAD_REQUEST);
		}

		if (subscriberLoginDetails.getId() != null)
			logger.info("Received request to update the sublogin info with credentials {}", subscriberLoginDetails);
		else
			logger.info("Received request to create the sublogin info with credetails {}", subscriberLoginDetails);
		return new ResponseEntity<SubscriberLoginDetails>(loginService.save(subscriberLoginDetails), HttpStatus.OK);

	}

	// Service for listing all user info details
	@RequestMapping(value = "/sublogin/list", method = RequestMethod.GET)
	public ResponseEntity<?> getAllSubscriberloginDetails(Pageable page) {
		logger.info("Received request to contact list all  info details.");
		Page<SubscriberLoginDetails> subscriberlist = loginService.listAllSubscriberLoginLst(page);
		logger.info("Result response: {}", subscriberlist);

		return new ResponseEntity<Page<SubscriberLoginDetails>>(subscriberlist, HttpStatus.OK);
	}

	// user login with otp service
	@RequestMapping(value = "/otp_login", method = RequestMethod.POST) 
	public ResponseEntity<?> getSubscriberloginByMsisdnOTP( @RequestBody final LoginWithOtp login ) {
		logger.info( "Received request for subscriber login with credentials {}", login );
		
		if( login == null || login.getMsisdn() == null || login.getMsisdn().equals("") ) {
			ErrorMessage error = new ErrorMessage("Field error","400","Bad request format.");
			return new ResponseEntity<ErrorMessage>( error, HttpStatus.BAD_REQUEST);
		}else{
			if( login.getMsisdn() != null && login.getGenerated_otp() == null ){
				Subscriber subscriber = subscriberService.checkSubscriberByMsisdn(login.getMsisdn());
				if( subscriber != null ){
					OtpResponse otp = smsService.sendSmsForOtp( subscriber.getMsisdn() );
					logger.info( "Otp has been sent successfully for login." );
					logger.info( otp.toString() );
					SubscriberLoginDetails subscriberLoginDetails = new SubscriberLoginDetails();
					subscriberLoginDetails.setLoginDate(new Date());
					subscriberLoginDetails.setSubscriber(subscriber);
					subscriberLoginDetails.setLoginMethod("true");
					//subscriberLoginDetails.setOtpExpireDate(new Date());
					
				 	Calendar c = Calendar.getInstance();
			        c.setTime(new Date());

			        // manipulate date
			       // c.add(Calendar.YEAR, 1);
			      //  c.add(Calendar.MONTH, 1);
			      //  c.add(Calendar.DATE, 1); //same with c.add(Calendar.DAY_OF_MONTH, 1);
			      //  c.add(Calendar.HOUR, 1);
			        c.add(Calendar.MINUTE, 5);
			      //  c.add(Calendar.SECOND, 1);
			        Date currentDatePlusOne = c.getTime();
			        subscriberLoginDetails.setOtpExpireDate(currentDatePlusOne);
			        
					subscriberLoginDetails.setGeneratedOtp(otp.getOtp());				
					loginService.save(subscriberLoginDetails);
					//subscriber.setPassword("XXXXXXX");
					return new ResponseEntity<OtpResponse>( otp, HttpStatus.OK );
				}else{
					logger.info("No record found for the subscriber with msisdn {}", login.getMsisdn());
					throw new ResourceNotFoundException(0, "No records found with the msisdn " + login.getMsisdn());
					//return new ResponseEntity<String>( "No record found for the subscriber with msisdn "+login.getMsisdn(), HttpStatus.OK );
				}
			}else if( login.getMsisdn() != null && login.getGenerated_otp() != null ){
				logger.info( "Initiate user login with otp for credentails {}", login );
				
				SimpleDateFormat today= new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");			
				Date now =new Date();
				
			    SubscriberLoginDetails subLogin = loginService.getSubscriberLoginByMsisdnOtp(login.getMsisdn(), login.getGenerated_otp());
			    if( subLogin != null ){
			    	Date previous=subLogin.getOtpExpireDate();
			    	if(previous.after(now)){
			    		return new ResponseEntity<SubscriberLoginDetails>( subLogin, HttpStatus.OK );
			    	}else{
			    		throw new ResourceNotFoundException(0, "Otp expire! Please retry again");
			    	}
			    }else{
			    	throw new ResourceNotFoundException(0,"Invalid OTP entered! Please enter correct OTP.");
			    }
				
				/*if(previous.after(now)){
					 if( subLogin != null ){
				
						 return new ResponseEntity<SubscriberLoginDetails>( subLogin, HttpStatus.OK );
						}else{
							throw new ResourceNotFoundException(0, "Login failed! Please try again." + login.getMsisdn() +"/"+login.getGenerated_otp());
							//return new ResponseEntity<String>( "Login failed! Please try again.", HttpStatus.OK );
						}
					
				}else{
					throw new ResourceNotFoundException(0,"Otp expire! Please retry again");
				}*/
				
				//logger.info("sub----------------"+sub);
				//logger.info("today--------------"+today.atStartOfDay(time));
				
			}
			
		}

	// return new ResponseEntity<SubscriberLoginDetails>( HttpStatus.OK);
	return null;

	}
}
