package com.crbt.api.services.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.crbt.api.services.domain.TopContent;

public interface TopContentRepository extends JpaRepository<TopContent, Integer>{
	@Query(value= "select id, song_id, song_name, category,category_id,CAST(song_order AS UNSIGNED) as song_order,is_active from top_content where is_active=1 order by song_order;",nativeQuery=true)
	List<Object[]> findAllTopContents();

	
}
