package com.crbt.api.services.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.crbt.api.services.domain.Subscriber;
import com.crbt.api.services.domain.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer>{

	//@Query("select sn from Subscription sn where sn.subscriber.id = :subscriber_id and sn.status = : status")
	
	@Query( "FROM Subscription WHERE subscriber.id = :id AND status = :status" )
	Subscription getActiveSubscriptionByIdAndStatus( @Param("id") Integer id, @Param("status") String status );
	
	@Query( "FROM Subscription WHERE subscriber.id = :id AND status = :status" )
	List<Subscription> getOldSubscriptionByIdAndStatus( @Param("id") Integer id, @Param("status") String status );

	//@Query("select sn from Subscription sn where sn.start_date =:start_date and sn.end_date =: end_date ")
	String nativeQuery="select *from subscription  where start_date>= :start_date and start_date<= :end_date";
	
	@Query(value = nativeQuery,nativeQuery=true)
	List<Subscription> DateRange(@Param("start_date")String start_date, @Param("end_date")String end_date);

	@Query( "FROM Subscription  sub WHERE sub.subscriber.msisdn = :msisdn ")
	Subscription findMsisdnBYSubscriberID(@Param("msisdn")String msisdn);

	@Query( "FROM Subscription WHERE subscriber = :subscriber AND ( status = :status OR status = 'D' OR status = 'S' OR status = 'F')" )
	Subscription getActiveSubscriptionBySubscriber( @Param("subscriber") Subscriber subscriber, @Param("status") String status );
	
}
