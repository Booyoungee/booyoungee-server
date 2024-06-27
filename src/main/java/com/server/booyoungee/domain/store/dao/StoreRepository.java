package com.server.booyoungee.domain.store.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.server.booyoungee.domain.store.domain.Store;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

	List<Store> findAllByDistrict(String district);

	@Query("SELECT s FROM Store s WHERE s.businessName LIKE %:businessName%")
	List<Store> findAllByBusinessName(@Param("businessName") String businessName);
}
