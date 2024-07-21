package com.server.booyoungee.domain.store.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.server.booyoungee.domain.store.domain.Store;

import jakarta.transaction.Transactional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

	List<Store> findAllByDistrict(String district);

	@Query("SELECT s FROM Store s WHERE s.businessName LIKE %:businessName%")
	List<Store> findAllByBusinessName(@Param("businessName") String businessName);

	@Query("SELECT s FROM Store s ORDER BY s.views DESC")
	Page<Store> findAllOrderByViews(Pageable pageable);

	Optional<Store> findByStoreId(Long storeId);

	@Modifying
	@Transactional
	@Query("UPDATE Store s SET s.views = 0")
	void resetViewsForAllStores();
}
