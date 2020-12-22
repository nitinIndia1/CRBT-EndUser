package com.crbt.api.services.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crbt.api.services.domain.Language;


public interface LanguageMasterRepository extends JpaRepository<Language, Integer>{

	/*@Query("select ln from LanguageMaster ln")
	java.util.List<Language> findAllData();*/

	
}