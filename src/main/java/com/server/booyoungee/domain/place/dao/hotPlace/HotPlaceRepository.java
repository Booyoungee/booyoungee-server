package com.server.booyoungee.domain.place.dao.hotPlace;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.server.booyoungee.domain.place.domain.HotPlace;

import jakarta.transaction.Transactional;

@Repository
public interface HotPlaceRepository extends JpaRepository<HotPlace, Long> {
	@Transactional
	@Modifying
	@Query("UPDATE HotPlace h SET h.isHotPlace = false")
	void updateAllToNotHotPlace();

	@Query("SELECT h FROM HotPlace h where h.isHotPlace = true")
	List<HotPlace> findAll();
}
