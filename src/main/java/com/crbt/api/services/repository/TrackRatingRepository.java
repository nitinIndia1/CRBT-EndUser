package com.crbt.api.services.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crbt.api.services.domain.Favorites;
import com.crbt.api.services.domain.Subscriber;
import com.crbt.api.services.domain.TopContent;
import com.crbt.api.services.domain.TrackRating;

public interface TrackRatingRepository extends JpaRepository<TrackRating, Integer>{

	List<TrackRating> findByTopContentOrderByRatingDesc(TopContent topContent);
	List<TrackRating> findByOrderByRatingDesc();
}
