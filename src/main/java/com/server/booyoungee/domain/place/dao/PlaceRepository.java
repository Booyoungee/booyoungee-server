package com.server.booyoungee.domain.place.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.server.booyoungee.domain.place.domain.Place;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {

	@Query("SELECT p FROM Place p  where p.viewCount>0 ORDER BY p.viewCount DESC")
	Page<Place> findTop10ByOrderByViewCountDesc(Pageable pageable);

	@Query(value = "SELECT D_TYPE FROM place WHERE id = :id", nativeQuery = true)
	String findDiscriminatorTypeById(@Param("id") Long id);

	//viewCount를 모두 0으로 초기화
	@Transactional
	@Modifying
	@Query("UPDATE Place p SET p.viewCount = 0")
	void updateAllViewCountToZero();

}
