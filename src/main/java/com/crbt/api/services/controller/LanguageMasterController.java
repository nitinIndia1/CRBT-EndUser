package com.crbt.api.services.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RestController;

import com.crbt.api.services.bean.ErrorMessage;
import com.crbt.api.services.domain.Language;
import com.crbt.api.services.domain.Role;
import com.crbt.api.services.service.LanguageMasterService;
import com.crbt.api.services.validator.LanguageMasterValidator;

@RestController
public class LanguageMasterController {

	private static final Logger logger = LoggerFactory.getLogger(LanguageMasterController.class);
	private final LanguageMasterService service;
	private final LanguageMasterValidator validator;

	@Inject
	public LanguageMasterController(LanguageMasterService service, LanguageMasterValidator validator) {
		this.service = service;
		this.validator = validator;
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);

	}

	// Service performing save or update by providing valid languageMaster info id
	@RequestMapping(value = "/language/create", method = RequestMethod.POST)
	public ResponseEntity<?> saveOrUpdateUserInfo(@Valid @RequestBody final Language languageMaster, Errors errors) {

		if (errors.hasErrors()) {
			logger.error("########### VALIDATION ERROR OCCURED: LanguageMaster Controller ##############");
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

		if (languageMaster.getId() != null)
			logger.info("Received request to update the languageMaster info with credentials {}", languageMaster);
		else
			logger.info("Received request to create the languageMaster info with credetails {}", languageMaster);
		return new ResponseEntity<Language>(service.save(languageMaster), HttpStatus.OK);
		
	}

	// Service for listing all languageMaster info details
	@RequestMapping(value = "/language/list1", method = RequestMethod.GET  )
	public ResponseEntity<?> getAllLanguageDetails(Pageable page){
		Page<Language> languagelist= service.listAllDetails(page);
		logger.info( "Result response: {}", languagelist );

		return new ResponseEntity<Page<Language>>(languagelist, HttpStatus.OK);
		
	}
//	@RequestMapping(value = "/language/list", method = RequestMethod.GET  )
//	public String getAllLanguageDetails() {
//		logger.info("Received request to languageMaster list all  info details.");
//		System.out.println(service.listAllLanguage());
//		return "success";
//	//	return service.listAllLanguage();
//	}

	
	
	

}
