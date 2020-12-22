package com.crbt.api.services.controller;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.crbt.api.services.bean.DateRange;
import com.crbt.api.services.bean.SubscriptionRequestBean;
import com.crbt.api.services.bean.SubscriptionResponseBean;
import com.crbt.api.services.domain.Subscription;
import com.crbt.api.services.exception.ResourceNotFoundException;
import com.crbt.api.services.service.SubscriptionService;
import com.crbt.api.services.validator.SubscriptionValidator;
//import com.ctc.wstx.io.EBCDICCodec;

@RestController
public class SubscriptionController {
	
	private static final Logger logger= LoggerFactory.getLogger(SubscriptionController.class);
	private final SubscriptionService service;
	private final SubscriptionValidator validator;
	
	@Inject
	public SubscriptionController(SubscriptionService service, SubscriptionValidator validator) {
		super();
		this.service = service;
		this.validator = validator;
	}
	
	 @InitBinder
		protected void initBinder(WebDataBinder binder) {
			binder.setValidator(validator);
        }
	
	//service for search subscription by subscriber id
	@RequestMapping(value = "/subscription", method = RequestMethod.POST)
	public SubscriptionResponseBean getSubscriptionDetailsBySubId( @Valid @RequestBody SubscriptionRequestBean subscriberRequestBean) {

		logger.info("Received request to list all the user under group subscriber {}", subscriberRequestBean.getSubscriber_id());
		
		return service.getSubscriptionByStatus(subscriberRequestBean.getSubscriber_id(), subscriberRequestBean.getStatus());
		/*if (subscription == null) {
			logger.info("NOT FOUND: " + "No subscriber information found under the subscriber group " + subscriberRequestBean.getSubscriber_id());
			throw new ResourceNotFoundException(0, "No subscription information found under the subscriber group " + subscriberRequestBean.getSubscriber_id() +"status"+subscriberRequestBean.getStatus());

		}
		return subscription;*/

	}

	//service for Subscription by id
	@RequestMapping(value = "/subscription/id/{id}", method = RequestMethod.POST)
	public Subscription getUserByUser_id(@Valid @PathVariable final Integer id) {
		logger.info("Received request to list user master info details for user_id {}", id);

		Subscription subscription = service.getSubscriptionByid(id);

		if (subscription == null) {
			logger.info("No records found with the subscription id " + id);
			throw new ResourceNotFoundException(0, "No records found with the subscription id " + id);
		}

		return subscription;

	}
	
	
	// Service for listing all user info details
		@RequestMapping(value = "/billing_history/all", method = RequestMethod.POST)
		public ResponseEntity<?> getAllUserDetails(Pageable page) {
			logger.info("Received request to list all user details.");
			Page<Subscription> subscriptionlist= service.listAllSubscription(page);
			
			logger.info( "Result response: {}", subscriptionlist );

			
			
			return new ResponseEntity<Page<Subscription>>(subscriptionlist, HttpStatus.OK);
			
		}
		
		// Service for Change Song Status By Song Id
		/*@RequestMapping(value = "/change/subscriber/song", method = RequestMethod.POST)
		//@JsonView(Views.WithMapping.class)
		public ResponseEntity<CoreResponseHandler> changeSong(@RequestBody final ChangeSongsBean pcb) {
			logger.info("Received request to list Subscriber info details for songId {} ,songStatus {}", pcb.getMsisdn(),
					pcb.getContent_songs_id());

			if (pcb.getMsisdn() != null && pcb.getContent_songs_id()!= null) {
				Subscription subscription = service.getChangeSong(pcb.getMsisdn());
				if (subscription == null) {
					// errors.rejectValue("msisdn", HttpStatus.BAD_REQUEST.name(),
					// "msisdn " + pcb.getMsisdn() + " is invalid ");
					logger.info("No records found with the MSISDN " + pcb.getMsisdn());
					logger.info("MSISDN or SONGID feild incorect");
					throw new ResourceNotFoundException(0,
							"No records found with the msisdn " + pcb.getMsisdn() + "/" + pcb.getContent_songs_id());

				} else {
					
					
					//subscription.setContentSongs(pcb.getContent_songs_id());
					//subscription.setSongStatus(pcb.getContent_songs_id());
					
					service.saveUpdateSongStatus(subscription);
				}
			} else {
				throw new ResourceNotFoundException(0, "songId & status can't be empty");
			}

			return new ResponseEntity(
					new SuccessResponseBean(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, "Request Successful"),
					HttpStatus.OK);

		}*/
		
		@RequestMapping(value="/billing_history" , method= RequestMethod.POST)
		public List<Subscription> dateRange(@Valid @RequestBody DateRange dateRange ){
			 List<Subscription> subscriptionList = service.getDataRangeDate(dateRange.getStart_date(),dateRange.getEnd_date());
			 logger.info("Received request to list all user details."+dateRange.getStart_date() +"/"+dateRange.getEnd_date());
				
			 return subscriptionList;
			
		}
}
