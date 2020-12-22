package com.crbt.api.services.controller;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.apache.tomcat.jni.FileInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.crbt.api.services.utils.CoreResponseHandler;
import com.crbt.api.services.utils.ResponseStatusEnum;
import com.crbt.api.services.utils.SuccessResponseBean;
import com.crbt.api.services.bean.ErrorMessage;
import com.crbt.api.services.bean.LanguageChangeBean;
import com.crbt.api.services.bean.LoginWithPassword;
import com.crbt.api.services.bean.PasswordChangeBean;
import com.crbt.api.services.bean.SubscriberProfileBean;
import com.crbt.api.services.bean.Views;
import com.crbt.api.services.domain.Language;
import com.crbt.api.services.domain.Subscriber;
import com.crbt.api.services.exception.ResourceNotFoundException;
import com.crbt.api.services.service.SubscriberService;
import com.crbt.api.services.validator.SubscriberValidator;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
public class SubscriberController {

	private static final Logger logger = LoggerFactory.getLogger(SubscriberController.class);
	@Value("${file.Window_SOURCE_FOLDER}")
	private String Window_SOURCE_FOLDER;
	private final SubscriberService service;
	private final SubscriberValidator validator;

	@Inject
	public SubscriberController(SubscriberService service, SubscriberValidator validator) {
		super();
		this.service = service;
		this.validator = validator;
	}

	@Autowired
	private ApplicationContext appContext;

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}

	/*--Subscriber update service ---*/
	@RequestMapping(value = "/subscriber/update", method = RequestMethod.POST)
	public ResponseEntity<?> update(@Valid @RequestBody final Subscriber subscriber) {
		if (subscriber.getId() != null) {
			return new ResponseEntity<Subscriber>(service.update(subscriber), HttpStatus.OK);
		} else {
			throw new ResourceNotFoundException(0, "No records found with the given Id" + subscriber.getId());
		}

	}

	/*--Subscriber create service ---*/

	@RequestMapping(value = "/subscriber/create", method = RequestMethod.POST)
	public ResponseEntity<?> saveOrUpdateUserInfo(@Valid @RequestBody Subscriber subscriber, Errors errors) {

		if (errors.hasErrors()) {
			logger.error("########### VALIDATION ERROR OCCURED: Subscriber Controller ##############");
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

		if (subscriber.getId() != null)
			logger.info("Received request to update the Subscriber info with credentials {}", subscriber);
		else
			logger.info("Received request to create the Subscriber info with credetails {}", subscriber);
		Subscriber subscriberSaved = service.save(subscriber);
		if (subscriberSaved != null) {

			return new ResponseEntity<Subscriber>(subscriber, HttpStatus.OK);
		} else {
			ErrorMessage subscriberError = new ErrorMessage();
			subscriberError.setErrorCode(HttpStatus.CONFLICT + "");
			subscriberError.setErrorField("msisdn");
			subscriberError.setErrorDescription("User msisdn already exist!");
			return new ResponseEntity<ErrorMessage>(subscriberError, HttpStatus.CONFLICT);
		}

	}

	/*
      Fetch subscriber details with subscriber id
*/
	@RequestMapping(value = "/subscriber/id/{id}", method = RequestMethod.GET)
	@JsonView(Views.Internal.class)
	public Subscriber getSubscriberBysibscriberId(@Valid @PathVariable final Integer id ) {
		logger.info("Received request to list user master info details for subscriberId {}", id);

		Subscriber subscriber = service.getsubscriberById(id);

		if (subscriber == null) {
			logger.info("No records found with the subscriber id " + id);
			throw new ResourceNotFoundException(0, "No records found with the subscriber id " + id);
		}

		return subscriber;

	}

	// Service for listing all user info details
	@RequestMapping(value = "/subscriber/list", method = RequestMethod.GET)
	public ResponseEntity<?> getAllSubscriberDetails(Pageable page) {
		logger.info("Received request to contact list all  info details.");
		Page<Subscriber> subscriber = service.listAllSubscriber(page);
		logger.info("Result response: {}", subscriber);

		return new ResponseEntity<Page<Subscriber>>(subscriber, HttpStatus.OK);
	}

	// user login service
	@RequestMapping(value = "/password_login", method = RequestMethod.POST)
	@JsonView( Views.Internal.class )
	public Subscriber getSubscriberByMsisdn(@RequestBody LoginWithPassword loginWithPassword) throws NoSuchAlgorithmException {
		logger.info("Received request to list Subscriber info details for msisdn {} ,password {}",
				loginWithPassword.getMsisdn(), loginWithPassword.getPassword());
		
		Subscriber subscriber = service.getSubscriberByMsisdn(loginWithPassword.getMsisdn(),
				loginWithPassword.getPassword());

		if (subscriber == null) {
			logger.info("No records found with the username ,password  " + loginWithPassword.getMsisdn()
					+ loginWithPassword.getPassword());
			logger.info("Username or password credentials are incorrect");
			throw new ResourceNotFoundException(0, "No records found with the username/ password ");

		}
		return subscriber;

	}

	// subscriber email verify service
	@RequestMapping(value = "/email_verification", method = RequestMethod.POST)
	@JsonView( Views.Internal.class )
	public List<Subscriber> getSubscriberByEmail(@Valid @RequestParam("email") final String email) {
		logger.info("Received request to list Subscriber info details for email {} ", email);

		List<Subscriber> subscriber = service.getSubscriberByEmail(email);

		if (subscriber == null) {
			logger.info("No records found with the email " + email);
			logger.info("Enter Email are incorrect");
			throw new ResourceNotFoundException(0, "No records found with the email " + email);

		}

		return subscriber;

	}

	// Service for change password

	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public ResponseEntity<CoreResponseHandler> changePassword(@RequestBody final PasswordChangeBean pcb) {
		logger.info("Received request to list Subscriber info details for msisdn {} ,generatedOtp {}", pcb.getMsisdn(),
				pcb.getPassword());

		if (pcb.getMsisdn() != null && pcb.getPassword() != null) {
			Subscriber subscriber = service.getChangePassword(pcb.getMsisdn());
			if (subscriber == null) {
				
				logger.info("No records found with the username ,generatedOtp  " + pcb.getMsisdn());
				logger.info("Msisdn or password incorect");
				throw new ResourceNotFoundException(0,
						"No records found with the msisdn " + pcb.getMsisdn() + "/" + pcb.getPassword());

			} else {
				subscriber.setPassword(pcb.getPassword());
				service.saveUpdatePassword(subscriber);
			}
		} else {
			throw new ResourceNotFoundException(0, "username & password can't be empty");
		}

		return new ResponseEntity(
				new SuccessResponseBean(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, "Request Successful"),
				HttpStatus.OK);

	}
	
	
	// Service for change language

	@RequestMapping(value = "/changeLanguage", method = RequestMethod.POST)
	public ResponseEntity<CoreResponseHandler> changeLanguage(@RequestBody final LanguageChangeBean pcb) {
		logger.info("Received request to list Subscriber info details for msisdn {} ,changeLanguage {}", pcb.getMsisdn(),
				pcb.getChangeLanguage());

		if (pcb.getMsisdn() != null && pcb.getChangeLanguage() != null) {
			Subscriber subscriber = service.getChangePassword(pcb.getMsisdn());
			if (subscriber == null) {
				
				logger.info("No records found with the msisdn  " + pcb.getMsisdn());
				//logger.info("Msisdn or password incorect");
				throw new ResourceNotFoundException(0,
						"No records found with the msisdn " + pcb.getMsisdn() + "/" + pcb.getChangeLanguage());

			} else {
				Subscriber s=new Subscriber();
				Language lang =new Language();
				lang.setId(pcb.getChangeLanguage());
			    subscriber.setChangeLanguage(lang);
				service.saveUpdateLanguage(subscriber);
			}
		} else {
			throw new ResourceNotFoundException(0, "msisdn & language can't be empty");
		}

		return new ResponseEntity(
				new SuccessResponseBean(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, "Request Successful"),
				HttpStatus.OK);

	}
	
/*
Update User Profile Pic
*/
	@RequestMapping(value = "/updateProfile", method = RequestMethod.POST)
	@ResponseStatus( HttpStatus.CREATED )
	public ResponseEntity<FileInfo> UpdateProfielPic(SubscriberProfileBean subscriberProfileBean,BindingResult result) throws IllegalStateException, IOException {
		logger.info("Received request to list Subscriber info details for msisdn {} " );
		
		System.out.println(subscriberProfileBean);
		if (result.hasErrors()) {
			throw new ResourceNotFoundException(0, "Field can't be empty");
		}else{
		Subscriber subscriber = service.getChangePassword(subscriberProfileBean.getMsisdn());
		if (subscriber == null) {
			logger.info("No records found with the msisdn " + subscriberProfileBean.getMsisdn());
			
			throw new ResourceNotFoundException(0,
					"No records found with the msisdn " + subscriberProfileBean.getMsisdn() );

		} else if(subscriberProfileBean.getFile()!=null)
		{
			
			//System.out.println(file);
			subscriber.setProfilePicPath("/Libyana/image" + "/" + subscriberProfileBean.getFile().getOriginalFilename());
			subscriberProfileBean.getFile().transferTo(new File(Window_SOURCE_FOLDER + "/" + subscriberProfileBean.getFile().getOriginalFilename()));
			System.out.println(Window_SOURCE_FOLDER);
			service.saveUpdateProfile(subscriber);
		
		}else{
			throw new ResourceNotFoundException(0, "Data not found");
		}
			
		return new ResponseEntity(
				new SuccessResponseBean(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, "Request Successful"),
				HttpStatus.OK);

	}
}
}
	
