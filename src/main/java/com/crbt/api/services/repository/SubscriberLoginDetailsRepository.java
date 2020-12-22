package com.crbt.api.services.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.crbt.api.services.domain.SubscriberLoginDetails;

public interface SubscriberLoginDetailsRepository extends JpaRepository<SubscriberLoginDetails, Integer>{

	//@Query("select sn from SubscriberLoginDetails join Subscriber s where s.msisdn = :msisdn and sn.generated_otp= :generated_otp")
	String nativeQuery = "select * from subscriber_login_details join subscriber where msisdn = :msisdn and generated_otp = :generated_otp";
	@Query( value = nativeQuery, nativeQuery = true )
	SubscriberLoginDetails findByMsisdnAndOtp(@Param("msisdn") String msisdn, @Param("generated_otp") String generated_otp);
	
	String nativeSearchBySubId = "select * from subscriber_login_details where subscriber_id = :id";
	@Query( value = nativeSearchBySubId, nativeQuery = true )
	SubscriberLoginDetails findBySubscriberId( @Param("id") Integer id );
}
