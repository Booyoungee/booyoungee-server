package com.server.booyoungee.domain.place.dao.store;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.server.booyoungee.domain.place.domain.storePlace.StorePlace;

import jakarta.transaction.Transactional;

@Repository
public interface StorePlaceRepository extends JpaRepository<StorePlace, Long> {

	List<StorePlace> findAllByDistrict(String district);

	@Query("SELECT s FROM StorePlace s WHERE s.businessName LIKE %:businessName%")
	List<StorePlace> findAllByBusinessName(@Param("businessName") String businessName);

	@Query("SELECT s FROM StorePlace s ORDER BY s.views DESC")
	Page<StorePlace> findAllOrderByViews(Pageable pageable);

	Optional<StorePlace> findByStoreId(Long storeId);

	@Modifying
	@Transactional
	@Query("UPDATE StorePlace s SET s.views = 0")
	void resetViewsForAllStores();
}
