package com.crbt.api.services.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
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

import com.captcha.botdetect.web.servlet.Captcha;
import com.crbt.api.services.bean.ErrorMessage;
import com.crbt.api.services.domain.Role;
import com.crbt.api.services.domain.User;
import com.crbt.api.services.service.RoleService;
import com.crbt.api.services.service.UserService;
import com.crbt.api.services.validator.RoleValidator;
import com.crbt.api.services.validator.UserValidator;

@RestController
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private final UserService service;
	private final UserValidator validator;

	@Inject
	public UserController(UserService service, UserValidator validator) {
		super();
		this.service = service;
		this.validator = validator;

	}
	

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}

	@RequestMapping(value= "/user/update", method = RequestMethod.POST)
	public ResponseEntity<?> update( @RequestBody final User user){
	return new ResponseEntity<User>(service.update(user), HttpStatus.OK);	
	}
	
	// Service performing save or update by providing valid user info id
	@RequestMapping(value = "/user/create", method = RequestMethod.POST)
	public ResponseEntity<?> saveUserInfo(@Valid @RequestBody final User user, Errors errors) {

		if (errors.hasErrors()) {
			logger.error("########### VALIDATION ERROR OCCURED: User Controller ##############");
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

		if (user.getId() != null)
			logger.info("Received request to update the user info with credentials {}", user);
		else
			logger.info("Received request to create the user info with credetails {}", user);
		return new ResponseEntity<User>(service.save(user), HttpStatus.OK);
		
	}

	/*
	 * service for get all user list
*/
	@RequestMapping(value = "/user/list", method = RequestMethod.GET  )
	public ResponseEntity<?> getAllUserDetails(Pageable page){
		Page<User> userlist= service.listAllUser(page);
		logger.info( "Result response: {}", userlist );

		return new ResponseEntity<Page<User>>(userlist, HttpStatus.OK);
		
	}

	
	@RequestMapping( value = "/user/validate_captcha", method = RequestMethod.POST )
	public ResponseEntity<?> validateCaptcha( HttpServletRequest request, @RequestParam("captchaVal") String captchaVal ){
		Captcha captcha = Captcha.load(request, "exampleCaptcha" );
		boolean isHuman = captcha.validate( captchaVal );
		if( isHuman ) {
			logger.info("Captcha is correct!");
		}else {
			logger.info("Captcha is incorrect!");
		}
		return null;
	}

}
