package com.crbt.api.services.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.multipart.MultipartFile;

import com.crbt.api.services.domain.Subscriber;

public interface SubscriberRepository extends JpaRepository<Subscriber, Integer>{

	Subscriber findByMsisdnAndPassword(String msisdn, String password);

	//Subscriber findOne(String email);

	@Query("from Subscriber sn where sn.email = :email")
	List<Subscriber> findByEmail( @Param("email") String email);

	//Subscriber findOne(String msisdn);

	Subscriber findByMsisdn(String msisdn);

	@Query("select sn from Subscriber sn where sn.msisdn = :msisdn ")
	Subscriber checkByMsisdn(@Param("msisdn") String msisdn);


	
}
