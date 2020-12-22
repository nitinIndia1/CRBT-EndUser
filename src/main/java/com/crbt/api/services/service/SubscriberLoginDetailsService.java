package com.crbt.api.services.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.crbt.api.services.domain.Subscriber;
import com.crbt.api.services.domain.SubscriberLoginDetails;

public interface SubscriberLoginDetailsService {

	SubscriberLoginDetails getSubscriberLoginByMsisdnOtp(String msisdn, String generated_otp);

	SubscriberLoginDetails save(SubscriberLoginDetails subscriberLoginDetails);

	Page<SubscriberLoginDetails> listAllSubscriberLoginLst(Pageable page);

}
