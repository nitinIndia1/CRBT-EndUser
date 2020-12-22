package com.crbt.api.services.service;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.crbt.api.services.domain.SubscriberLoginDetails;
import com.crbt.api.services.repository.SubscriberLoginDetailsRepository;

@Service
@Validated
public class SubscriberLoginDetailsServiceImpl implements SubscriberLoginDetailsService{
	private static final Logger logger= LoggerFactory.getLogger(SubscriberLoginDetailsServiceImpl.class);
	private final SubscriberLoginDetailsRepository repository;
	
	@Inject
	public SubscriberLoginDetailsServiceImpl(SubscriberLoginDetailsRepository repository) {
		super();
		this.repository = repository;
	}
	@Override
	public SubscriberLoginDetails getSubscriberLoginByMsisdnOtp(String msisdn, String generated_otp) {
		// TODO Auto-generated method stub
      logger.info( "Received request to list all the details of Subscriber." );
		
		return repository.findByMsisdnAndOtp(msisdn, generated_otp);
	}
	@Override
	public SubscriberLoginDetails save(SubscriberLoginDetails subscriberLoginDetails) {
		SubscriberLoginDetails sld = repository.findBySubscriberId( subscriberLoginDetails.getSubscriber().getId() );
		logger.info("---1---"+subscriberLoginDetails);
		if( sld != null ){
			subscriberLoginDetails.setId( sld.getId() );
			logger.info("-------2---------"+subscriberLoginDetails);
			return repository.save( subscriberLoginDetails );
		}else{
			return repository.save( subscriberLoginDetails );
		}
		
	}
	@Override
	public Page<SubscriberLoginDetails> listAllSubscriberLoginLst(Pageable page) {
		// TODO Auto-generated method stub
		return repository.findAll(page);
	}

}
