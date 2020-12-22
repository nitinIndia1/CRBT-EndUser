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
import com.crbt.api.services.domain.User;
import com.crbt.api.services.repository.RoleRepository;
import com.crbt.api.services.repository.UserRepository;

@Service 
@Validated
public class UserServiceImpl implements UserService{

	private static final Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);
	private final UserRepository repository;
	
	@Inject
	public UserServiceImpl(UserRepository repository) {
		super();
		this.repository = repository;
	}
	
	@Override
	public Page<User> listAllUser(Pageable page) {
		// TODO Auto-generated method stub
		logger.info( "Received request to list all the details of user." +page);
		
		return repository.findAll(page);
	}

	

	@Override
	public User update(User user) {
		logger.info("update role record info"+user);
		user.setCreatedOn(user.getCreatedOn());
		user.setUpdateOn(new Date());
		return repository.saveAndFlush(user);
	}

	@Override
	public User save(User user) {
		logger.info( "Received request to create new entry for user." );
		logger.info( "Creating {}", user );
		user.setCreatedOn(new Date());
		user.setUpdateOn(new Date());
		return repository.save(user);}

	@Override
	public User getUserById(Integer id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

}
