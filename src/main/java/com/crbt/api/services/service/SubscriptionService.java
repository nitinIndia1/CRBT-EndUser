package com.crbt.api.services.service;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.crbt.api.services.bean.SubscriptionResponseBean;
import com.crbt.api.services.domain.Subscriber;
import com.crbt.api.services.domain.Subscription;

public interface SubscriptionService {

	//Subscription getSubscriptionBysubscriberId( @NotNull @Valid final Integer subscriber_id,  String currentSubscription,
	//		String previousSubscription);

	Subscription getSubscriptionByid(@NotNull @Valid final Integer id);

	Page<Subscription> listAllSubscription(Pageable page);

	List<Subscription> getDataRangeDate(String start_date, String end_date) ;
		// TODO Auto-generated method stub

	SubscriptionResponseBean getSubscriptionByStatus(Integer subscriber_id, String status);

	Subscription getChangeSong(String msisdn);

	void saveUpdateSongStatus(Subscription subscription);
	
//	List<Subscription>getSubscriptionByInActiveStatus(Integer subscriber_id, String status);
	
	Subscription getActiveSubscriptionBySubscriber( final Subscriber subscriber );

	Subscription getSubscriptionByIdAndStatus(Integer subscriber_id, String status);


	
}
