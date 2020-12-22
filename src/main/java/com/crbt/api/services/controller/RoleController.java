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
import com.crbt.api.services.domain.Role;
import com.crbt.api.services.service.RoleService;
import com.crbt.api.services.validator.RoleValidator;

@RestController
public class RoleController {

	private static final Logger logger = LoggerFactory.getLogger(RoleController.class);
	private final RoleService service;
	private final RoleValidator validator;

	@Inject
	public RoleController(RoleService service, RoleValidator validator) {
		super();
		this.service = service;
		this.validator = validator;

	}
	

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}

	@RequestMapping(value= "/role/update", method = RequestMethod.POST)
	public ResponseEntity<?> update( @RequestBody final Role role){
	return new ResponseEntity<Role>(service.update(role), HttpStatus.OK);	
	}
	
	// Service performing save or update by providing valid user info id
	@RequestMapping(value = "/role/create", method = RequestMethod.POST)
	public ResponseEntity<?> saveOrUpdateRoleInfo(@Valid @RequestBody final Role role, Errors errors) {

		if (errors.hasErrors()) {
			logger.error("########### VALIDATION ERROR OCCURED: Role Controller ##############");
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

		if (role.getId() != null)
			logger.info("Received request to update the role info with credentials {}", role);
		else
			logger.info("Received request to create the role info with credetails {}", role);
		return new ResponseEntity<Role>(service.save(role), HttpStatus.OK);
		
	}

	/*
	 * service for get all role list
*/
	@RequestMapping(value = "/role/list", method = RequestMethod.GET  )
	public ResponseEntity<?> getAllRoleDetails(Pageable page){
		Page<Role> rolelist= service.listAllRole(page);
		logger.info( "Result response: {}", rolelist );

		return new ResponseEntity<Page<Role>>(rolelist, HttpStatus.OK);
		
	}

}
