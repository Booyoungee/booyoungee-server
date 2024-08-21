package com.server.booyoungee.domain.place.dao.store;

import java.util.List;
import java.util.Optional;

import com.server.booyoungee.domain.place.domain.movie.MoviePlace;
import org.springframework.data.domain.PageRequest;
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

	@Query("SELECT s FROM StorePlace s where s.viewCount>0 ORDER BY s.viewCount DESC")
	List<StorePlace> top10StorePlace(PageRequest of);


	@Query("SELECT sp FROM StorePlace sp " +
			"WHERE ST_Distance_Sphere(Point(cast(sp.mapX as double), cast(sp.mapY as double)), " +
			"Point(:longitude, :latitude)) <= :radius " +
			"ORDER BY ST_Distance_Sphere(Point(cast(sp.mapX as double), cast(sp.mapY as double)), " +
			"Point(:longitude, :latitude))")
	List<StorePlace> findNearbyStorePlaces(
			@Param("latitude") double latitude,
			@Param("longitude") double longitude,
			@Param("radius") double radius);

}
