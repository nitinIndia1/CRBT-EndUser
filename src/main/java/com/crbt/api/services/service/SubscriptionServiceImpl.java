package com.crbt.api.services.service;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.crbt.api.services.bean.ChangeSongsBean;
import com.crbt.api.services.bean.SubscriptionResponseBean;
import com.crbt.api.services.domain.Subscriber;
import com.crbt.api.services.domain.Subscription;
import com.crbt.api.services.repository.SubscriberRepository;
import com.crbt.api.services.repository.SubscriptionRepository;

@Service
@Validated
public class SubscriptionServiceImpl implements SubscriptionService{


	private static final Logger logger= LoggerFactory.getLogger(SubscriptionServiceImpl.class);
	private final SubscriptionRepository repository;
	
	@Inject
	public SubscriptionServiceImpl(SubscriptionRepository repository) {
		super();
		this.repository = repository;
	}
//	@Override
//	public Subscription getSubscriptionBysubscriberId(Integer subscriber_id) {
//		// TODO Auto-generated method stub
//		return repository.getSubscriptionBySubscriberId(subscriber_id);
//	}
	@Override
	public Subscription getSubscriptionByid(Integer id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}
	@Override
	public Page<Subscription> listAllSubscription(Pageable page) {
		logger.info( "Received request to list all the details of subscription." );
		return repository.findAll(page);
	}
	@Override
	public List<Subscription> getDataRangeDate(String start_date, String end_date) {
		// TODO Auto-generated method stub
		return repository.DateRange(start_date,end_date);
	}
	@Override
	public SubscriptionResponseBean getSubscriptionByStatus(Integer subscriber_id, String status) {
		SubscriptionResponseBean response = new SubscriptionResponseBean();
		response.setCurrentSubscription( repository.getActiveSubscriptionByIdAndStatus(subscriber_id, "Y") );
		response.setOldSubscription( repository.getOldSubscriptionByIdAndStatus(subscriber_id, "N") );		
		return response;
	}
	@Override
	public Subscription getChangeSong(String msisdn) {
		// TODO Auto-generated method stub
		return repository.findMsisdnBYSubscriberID(msisdn);
	}
	@Override
	public void saveUpdateSongStatus(Subscription subscription) {
		// TODO Auto-generated method stub
		
		repository.saveAndFlush(subscription);
		
	}
	@Override
	public Subscription getActiveSubscriptionBySubscriber(Subscriber subscriber) {
		logger.info( "Received request to get active subscription for the subscriber id: {}", subscriber.getId() );
		return repository.getActiveSubscriptionBySubscriber( subscriber, "Y" );
	}
	@Override
	public Subscription getSubscriptionByIdAndStatus(Integer subscriber_id, String status) {
		return repository.getActiveSubscriptionByIdAndStatus(subscriber_id, status);
	}
	

}
