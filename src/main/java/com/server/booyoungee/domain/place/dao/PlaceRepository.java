package com.server.booyoungee.domain.place.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.server.booyoungee.domain.place.domain.Place;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {

	@Query("SELECT p FROM Place p  where p.viewCount>0 ORDER BY p.viewCount DESC")
	Page<Place> findTop10ByOrderByViewCountDesc(Pageable pageable);
}
