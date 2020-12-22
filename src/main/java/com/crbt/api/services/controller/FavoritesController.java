package com.crbt.api.services.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.crbt.api.services.bean.DebitBalance;
import com.crbt.api.services.bean.FavoritesBean;
import com.crbt.api.services.bean.GiftData;
import com.crbt.api.services.bean.HssBean;
import com.crbt.api.services.domain.ContentSongs;
import com.crbt.api.services.domain.Favorites;
import com.crbt.api.services.domain.Language;
import com.crbt.api.services.domain.Subscriber;
import com.crbt.api.services.domain.Subscription;
import com.crbt.api.services.domain.TopContent;
import com.crbt.api.services.repository.FavoritesRepository;
import com.crbt.api.services.repository.SubscriberRepository;
import com.crbt.api.services.repository.TopContentRepository;
import com.crbt.api.services.service.ContentSongsService;
import com.crbt.api.services.service.LanguageMasterService;
import com.crbt.api.services.service.SubscriberService;
import com.crbt.api.services.service.SubscriptionService;
import com.crbt.api.services.utils.ApplicationResponse;
import com.crbt.api.services.utils.CoreResponseHandler;
import com.crbt.api.services.utils.ResponseStatusEnum;
import com.crbt.api.services.utils.SuccessResponseBeanRefined;

@RestController
public class FavoritesController {

	private static final Logger logger = LoggerFactory
			.getLogger(FavoritesController.class);


	@Autowired
	private FavoritesRepository favoritesRepository;

	@Autowired
	private SubscriberRepository subscriberRepository;


	@Autowired
	private TopContentRepository topContentRepository;


	@PostMapping(value="favorites",consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CoreResponseHandler> favorites(@RequestBody final FavoritesBean favoritesBean){
		long l_diff = 0;
		long l_time_start = System.currentTimeMillis();
		try {
		Favorites favorites = new Favorites();
		Subscriber subscriber =  subscriberRepository.getOne(Integer.parseInt(favoritesBean.getSubscriber_id()));
		if(subscriber==null) {
			long l_end_time = System.currentTimeMillis();
			l_diff = l_end_time-l_time_start;
			return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.NOT_FOUND, ResponseStatusEnum.FAILED, ApplicationResponse.Failed, "subscriber not found",l_diff+" ms"),HttpStatus.NOT_FOUND);
		}
		TopContent topContent =  topContentRepository.getOne(Integer.parseInt(favoritesBean.getTop_content_id()));
		if(topContent==null) {
			long l_end_time = System.currentTimeMillis();
			l_diff = l_end_time-l_time_start;
			return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.NOT_FOUND, ResponseStatusEnum.FAILED, ApplicationResponse.Failed, "track not found",l_diff+" ms"),HttpStatus.NOT_FOUND);			
		}

		favorites.setSubscriberId(subscriber);

		favorites.setTopContentId(topContent);

		List<Favorites> list = favoritesRepository.findBySubscriberIdAndTopContentId(subscriber, topContent);

		if(list!=null && list.size()>0) {
			long l_end_time = System.currentTimeMillis();
			l_diff = l_end_time-l_time_start;
			return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL, "this favorite already exists",l_diff+" ms") ,HttpStatus.OK);
		}

		Favorites favorites2 =  favoritesRepository.saveAndFlush(favorites);
		if(favorites2!=null) {
			long l_end_time = System.currentTimeMillis();
			l_diff = l_end_time-l_time_start;
			return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL, "favorite saved!",l_diff+" ms") ,HttpStatus.OK);
		}
		}catch(Exception ex) {
			ex.printStackTrace();
			long l_end_time = System.currentTimeMillis();
			l_diff = l_end_time-l_time_start;
			return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"Exception",l_diff+" ms"),HttpStatus.INTERNAL_SERVER_ERROR);			
		}
		long l_end_time = System.currentTimeMillis();
		l_diff = l_end_time-l_time_start;
		return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"ERROR",l_diff+" ms"),HttpStatus.INTERNAL_SERVER_ERROR);
	}








	@GetMapping(value="getFavorites/{id}",consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CoreResponseHandler> getFavorites(@PathVariable(value="id",required=true)int id){
		long l_diff = 0;
		long l_time_start = System.currentTimeMillis();
		Subscriber subscriber =  subscriberRepository.getOne(id);
				
		List<Favorites> list = favoritesRepository.findBySubscriberId(subscriber);

		if(list==null || list.size()==0) {
			long l_end_time = System.currentTimeMillis();
			l_diff = l_end_time-l_time_start;
			return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.NOT_FOUND, ResponseStatusEnum.FAILED, ApplicationResponse.Failed, "no data found",l_diff+" ms"),HttpStatus.NOT_FOUND);
		}
		else if(list!=null && list.size()>0){
			long l_end_time = System.currentTimeMillis();
			l_diff = l_end_time-l_time_start;
			return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL, list,l_diff+" ms") ,HttpStatus.OK);			
		}
		long l_end_time = System.currentTimeMillis();
		l_diff = l_end_time-l_time_start;
		return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"ERROR",l_diff+" ms"),HttpStatus.INTERNAL_SERVER_ERROR);
	}



	@PostMapping(value="deleteFavorite",consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CoreResponseHandler> deleteFavorite(@RequestBody final FavoritesBean favoritesBean){
		long l_diff = 0;
		long l_time_start = System.currentTimeMillis();
		
		Subscriber subscriber =  subscriberRepository.getOne(Integer.parseInt(favoritesBean.getSubscriber_id()));
		if(subscriber==null) {
			long l_end_time = System.currentTimeMillis();
			l_diff = l_end_time-l_time_start;
			return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.NOT_FOUND, ResponseStatusEnum.FAILED, ApplicationResponse.Failed, "subscriber not found",l_diff+" ms"),HttpStatus.NOT_FOUND);
		}
		TopContent topContent =  topContentRepository.getOne(Integer.parseInt(favoritesBean.getTop_content_id()));
		if(topContent==null) {
			long l_end_time = System.currentTimeMillis();
			l_diff = l_end_time-l_time_start;
			return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.NOT_FOUND, ResponseStatusEnum.FAILED, ApplicationResponse.Failed, "track not found",l_diff+" ms"),HttpStatus.NOT_FOUND);			
		}

		List<Favorites> list = favoritesRepository.findBySubscriberIdAndTopContentId(subscriber, topContent);

		if(list==null || list.size()==0) {
			long l_end_time = System.currentTimeMillis();
			l_diff = l_end_time-l_time_start;
			return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.NOT_FOUND, ResponseStatusEnum.FAILED, ApplicationResponse.Failed, "favorite not found",l_diff+" ms"),HttpStatus.NOT_FOUND);			
		}

		Favorites favorites2 = list.get(0);

		
		favoritesRepository.delete(favorites2);

		long l_end_time = System.currentTimeMillis();
		l_diff = l_end_time-l_time_start;
		return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL, "favorite deleted",l_diff+" ms") ,HttpStatus.OK);

	}






}
