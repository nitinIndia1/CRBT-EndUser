package com.crbt.api.services.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crbt.api.services.domain.Favorites;
import com.crbt.api.services.domain.Subscriber;
import com.crbt.api.services.domain.TopContent;

public interface FavoritesRepository extends JpaRepository<Favorites, Integer>{

	List<Favorites> findBySubscriberId(Subscriber subscriber);
	
	List<Favorites> findBySubscriberIdAndTopContentId(Subscriber subscriber,TopContent topContent);
	
}
