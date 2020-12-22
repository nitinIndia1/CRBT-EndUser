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
import com.crbt.api.services.bean.TrackRatingBean;
import com.crbt.api.services.domain.ContentSongs;
import com.crbt.api.services.domain.Favorites;
import com.crbt.api.services.domain.Language;
import com.crbt.api.services.domain.Subscriber;
import com.crbt.api.services.domain.Subscription;
import com.crbt.api.services.domain.TopContent;
import com.crbt.api.services.domain.TrackRating;
import com.crbt.api.services.repository.FavoritesRepository;
import com.crbt.api.services.repository.SubscriberRepository;
import com.crbt.api.services.repository.TopContentRepository;
import com.crbt.api.services.repository.TrackRatingRepository;
import com.crbt.api.services.service.ContentSongsService;
import com.crbt.api.services.service.LanguageMasterService;
import com.crbt.api.services.service.SubscriberService;
import com.crbt.api.services.service.SubscriptionService;
import com.crbt.api.services.utils.ApplicationResponse;
import com.crbt.api.services.utils.CoreResponseHandler;
import com.crbt.api.services.utils.ResponseStatusEnum;
import com.crbt.api.services.utils.SuccessResponseBeanRefined;

@RestController
public class TrackRatingController {

	private static final Logger logger = LoggerFactory
			.getLogger(TrackRatingController.class);


	@Autowired
	private TrackRatingRepository trackRatingRepository;

	@Autowired
	private SubscriberRepository subscriberRepository;


	@Autowired
	private TopContentRepository topContentRepository;


	@PostMapping(value="trackRating",consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CoreResponseHandler> trackRating(@RequestBody final TrackRatingBean trackRatingBean){
		long l_diff = 0;
		long l_time_start = System.currentTimeMillis();
		String strRating = trackRatingBean.getRating();
		try {
		if(strRating==null||strRating.equals("")||Integer.parseInt(strRating)>5) {
			long l_end_time = System.currentTimeMillis();
			l_diff = l_end_time-l_time_start;
			return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.BAD_REQUEST, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"Allowed rating range 0-5",l_diff+" ms"),HttpStatus.BAD_REQUEST);			
		}

		TrackRating trackRating = new TrackRating();
		
		TopContent topContent =  topContentRepository.findOne(Integer.parseInt(trackRatingBean.getTop_content_id()));
		
		if(topContent==null) {
			long l_end_time = System.currentTimeMillis();
			l_diff = l_end_time-l_time_start;
			return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.NOT_FOUND, ResponseStatusEnum.FAILED, ApplicationResponse.Failed, "track not found",l_diff+" ms"),HttpStatus.NOT_FOUND);			
		}
		String strRemarks = trackRatingBean.getRemarks();
		trackRating.setTopContent(topContent);
		trackRating.setRating(Integer.parseInt(strRating));
		trackRating.setRemarks(strRemarks);
		trackRating.setCreateDate(new Date());
		
		TrackRating trackRating2 =  trackRatingRepository.saveAndFlush(trackRating);
		if(trackRating2!=null) {
			long l_end_time = System.currentTimeMillis();
			l_diff = l_end_time-l_time_start;
			return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL, "rating saved!",l_diff+" ms") ,HttpStatus.OK);
		}
		}catch(Exception ex) {
			long l_end_time = System.currentTimeMillis();
			l_diff = l_end_time-l_time_start;
			return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"ERROR",l_diff+" ms"),HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		long l_end_time = System.currentTimeMillis();
		l_diff = l_end_time-l_time_start;
		return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"ERROR",l_diff+" ms"),HttpStatus.INTERNAL_SERVER_ERROR);
		
	}








	@GetMapping(value="getRatingByTopContentId/{id}",consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CoreResponseHandler> getRatingsByTrackId(@PathVariable(value="id",required=true)int id){
		long l_diff = 0;
		long l_time_start = System.currentTimeMillis();
		TopContent topContent =  topContentRepository.findOne(id);		
		List<TrackRating> list = trackRatingRepository.findByTopContentOrderByRatingDesc(topContent);
		
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

	@GetMapping(value="getAllRating",consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CoreResponseHandler> getAllRatings(){
		long l_diff = 0;
		long l_time_start = System.currentTimeMillis();
		List<TrackRating> list = trackRatingRepository.findByOrderByRatingDesc();
		
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

	


}
