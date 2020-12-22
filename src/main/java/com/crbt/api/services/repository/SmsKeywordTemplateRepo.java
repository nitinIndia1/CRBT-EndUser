package com.crbt.api.services.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.crbt.api.services.domain.SmsKeywordTemplate;

public interface SmsKeywordTemplateRepo extends JpaRepository<SmsKeywordTemplate, Integer>{

	@Query( "FROM SmsKeywordTemplate sms WHERE sms.smsKeywordEn = :keyword OR sms.smsKeywordAr = :keyword" )
	SmsKeywordTemplate getSmsTemplateByKeyword( @Param("keyword") String keyword );
}
