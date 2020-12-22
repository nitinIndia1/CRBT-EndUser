package com.crbt.api.services.service;

import java.util.Date;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.crbt.api.services.domain.Role;
import com.crbt.api.services.repository.RoleRepository;


@Service
@Validated
public class RoleServiceImpl implements RoleService{
	
	private static final Logger logger= LoggerFactory.getLogger(RoleServiceImpl.class);
	private final RoleRepository repository;
	
	@Inject
	public RoleServiceImpl(RoleRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public Role update(Role role) {
		// TODO Auto-generated method stub
		
		logger.info("update role record info"+role);
		role.setCreatedOn(role.getCreatedOn());
		role.setUpdatedOn(new Date());
		return repository.saveAndFlush(role);
	}

	@Override
	public Role save(Role role) {
		logger.info( "Received request to create new entry for role." );
		logger.info( "Creating {}", role );
		role.setCreatedOn(new Date());
		role.setUpdatedOn(new Date());
		return repository.save(role);
	}

	@Override
	public Page<Role> listAllRole(Pageable page) {
		logger.info( "Received request to list all the details of role." );
		return repository.findAll(page);
	}
	
	
	
	
}
