package com.crbt.api.services.service;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.crbt.api.services.bean.LanguageChangeBean;
import com.crbt.api.services.domain.Language;
import com.crbt.api.services.domain.Subscriber;
import com.crbt.api.services.exception.ResourceNotFoundException;
import com.crbt.api.services.repository.SubscriberRepository;

@Service
@Validated
public class SubscriberServiceImpl implements SubscriberService {

	private static final Logger logger = LoggerFactory.getLogger(SubscriberServiceImpl.class);
	private final SubscriberRepository repository;

	@Inject
	public SubscriberServiceImpl(SubscriberRepository repository) {
		super();
		this.repository = repository;
	}

	public Page<Subscriber> listAllSubscriber(Pageable page) {
		logger.info("Received request to list all the details of subscriber list.");
		return repository.findAll(page);
	}

	@Override
	public Subscriber save(Subscriber subscriber) {
		// TODO Auto-generated method stub
		// return repository.save(subscriber);
		logger.info("Received request to create new entry for subscriber.");
		logger.info("Creating {}", subscriber);
		Subscriber saveUser = null;
		try {
			if (subscriber.getChangeLanguage() != null) {
				//subscriber.setChangeLanguage(subscriber.getChangeLanguage());
				Language lang = new Language();
				
				subscriber.setCreatedOn(new Date());
				subscriber.setUpdatedOn(new Date());
				saveUser = repository.save(subscriber);

			} else {
				Language lang = new Language();
				lang.setId(1);
				subscriber.setChangeLanguage(lang);
				subscriber.setCreatedOn(new Date());
				subscriber.setUpdatedOn(new Date());

				saveUser = repository.save(subscriber);

			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return saveUser;
	}

	@Override
	public Subscriber getSubscriberByMsisdn(String msisdn, String password) {
		logger.info("Received request to list all the details of Subscriber.");

		return repository.findByMsisdnAndPassword(msisdn, password);
	}

	@Override
	public Subscriber getSubscriberByMsisdnOnly(String msisdn) {
		logger.info( "Received request to get subscriber details for the msisdn: {}", msisdn );
		return repository.checkByMsisdn( msisdn );
	}
	
	@Override
	public List<Subscriber> getSubscriberByEmail(String email) {
		// TODO Auto-generated method stub
		return repository.findByEmail(email);
	}

	@Override
	public Subscriber getChangePassword(String msisdn) {
		// TODO Auto-generated method stub
		return repository.findByMsisdn(msisdn);
	}

	@Override
	public void saveUpdatePassword(Subscriber subscriber) {
		// TODO Auto-generated method stub
		subscriber.setUpdatedOn(new Date());
		repository.saveAndFlush(subscriber);

	}

	@Override
	public Subscriber checkSubscriberByMsisdn(String msisdn) {
		// TODO Auto-generated method stub
		return repository.checkByMsisdn(msisdn);
	}

	@Override
	public Subscriber update(Subscriber subscriber) {
		Subscriber update = repository.getOne(subscriber.getId());
		if (update != null) {
			// update.setMsisdn(subscriber.getMsisdn());
			// update.setPassword(subscriber.getPassword());
			// update.setEmail( subscriber.getEmail() );
			update.setLocation(subscriber.getLocation());
			// update.setProfilePicPath(subscriber.getProfilePicPath());
			update.setIsAppUser(subscriber.getIsAppUser());
			update.setDeviceType(subscriber.getDeviceType());
			update.setIsActive(subscriber.getIsActive());
			// update.setCreatedOn(subscriber.getCreatedOn());
			update.setUpdatedOn(new Date());

			repository.save(update);
		}

		return update;
	}

	@Override
	public void saveUpdateProfile(Subscriber subscriber) {

		subscriber.setUpdatedOn(new Date());
		repository.saveAndFlush(subscriber);

	}

	@Override
	public Subscriber getsubscriberById(Integer id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

	@Override
	public void saveUpdateLanguage(Subscriber subscriber) {
		// TODO Auto-generated method stub
		subscriber.setUpdatedOn(new Date());
		repository.saveAndFlush(subscriber);

	}
	
}
