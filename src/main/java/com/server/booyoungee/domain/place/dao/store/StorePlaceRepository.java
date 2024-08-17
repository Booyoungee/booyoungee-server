package com.server.booyoungee.domain.place.dao.store;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.server.booyoungee.domain.place.domain.store.StorePlace;

import jakarta.transaction.Transactional;

@Repository
public interface StorePlaceRepository extends JpaRepository<StorePlace, Long> {

	List<StorePlace> findAllByDistrict(String district);

	@Query("SELECT s FROM StorePlace s WHERE s.name LIKE %:name%")
	List<StorePlace> findAllByName(@Param("name") String businessName);

	@Query("SELECT s FROM StorePlace s ORDER BY s.viewCount DESC")
	List<StorePlace> findAllOrderByViewCount(Pageable pageable);

	Optional<StorePlace> findByStoreId(Long storeId);

	@Modifying
	@Transactional
	@Query("UPDATE StorePlace s SET s.viewCount = 0")
	void resetViewsForAllStores();
}
