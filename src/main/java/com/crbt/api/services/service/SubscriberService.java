package com.crbt.api.services.service;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.crbt.api.services.domain.Subscriber;

public interface SubscriberService {

	Page<Subscriber> listAllSubscriber(Pageable page);

	Subscriber save(@NotNull @Valid final Subscriber subscriber);

	Subscriber getSubscriberByMsisdn(String msisdn, String password);
	
	Subscriber getSubscriberByMsisdnOnly(String msisdn);

	List<Subscriber> getSubscriberByEmail(@NotNull @Valid final String email);
	
	Subscriber checkSubscriberByMsisdn(@NotNull @Valid final String msisdn);

	Subscriber getChangePassword(String msisdn);

	void saveUpdatePassword(Subscriber subscriber);

	Subscriber update(@NotNull @Valid final Subscriber subscriber);

	void saveUpdateProfile(Subscriber subscriber);

	Subscriber getsubscriberById(@Valid @NotNull final Integer id);

	void saveUpdateLanguage(Subscriber subscriber);

	
}
