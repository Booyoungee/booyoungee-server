package com.server.booyoungee.domain.place.dao.recommend;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.server.booyoungee.domain.place.domain.RecommendPlace;

import jakarta.transaction.Transactional;

import java.util.List;

@Repository
public interface RecommendRepository extends JpaRepository<RecommendPlace, Long> {

	@Transactional
	@Modifying
	@Query("UPDATE RecommendPlace h SET h.isRecommendPlace = false")
	void updateAllToNotRecommendPlace();

	@Query("SELECT h FROM RecommendPlace h WHERE h.isRecommendPlace = true ORDER BY h.createdAt DESC")
	Page<RecommendPlace> findAll(Pageable pageable);

	@Query("SELECT CASE WHEN COUNT(h) > 0 THEN true ELSE false END FROM RecommendPlace h WHERE h.isRecommendPlace = true AND h.place.id = :placeId")
	boolean existsByPlaceId(@Param("placeId") Long placeId);


}
