package com.server.booyoungee.domain.place.dao.movie;

import java.util.List;

import com.server.booyoungee.domain.place.domain.store.StorePlace;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.server.booyoungee.domain.place.domain.movie.MoviePlace;

@Repository
public interface MoviePlaceRepository extends JpaRepository<MoviePlace, Long> {

	Long countByMovieNameContaining(String keyword);

	Long countByNameContaining(String keyword);

	List<MoviePlace> findAllByNameContainingOrderByViewCount(String keyword, Pageable pageable);

	List<MoviePlace> findAllByMovieNameContainingOrderByViewCount(String keyword, Pageable pageable);

	@Query("SELECT mp FROM MoviePlace mp "
		+ "WHERE (6371000 * acos( cos( radians(CAST(:mapY AS double)) ) "
		+ "* cos( radians(CAST(mp.mapY AS double)) ) "
		+ "* cos( radians(CAST(mp.mapX AS double)) "
		+ "- radians(CAST(:mapX AS double)) ) "
		+ "+ sin( radians(CAST(:mapY AS double)) ) "
		+ "* sin( radians(CAST(mp.mapY AS double)) ) )) <= :radius")
	List<MoviePlace> findMoviePlacesByMapXAndMapY(
		@Param("mapX") String mapX,
		@Param("mapY") String mapY,
		@Param("radius") int radius);

	@Query("SELECT s FROM MoviePlace s ORDER BY s.viewCount DESC")
	List<MoviePlace> findAllOrderByViewCount(Pageable pageable);

	@Query("SELECT s FROM MoviePlace s where s.viewCount>0 ORDER BY s.viewCount DESC")
	List<MoviePlace> top10MoviePlace(Pageable pageable);

	@Query("SELECT sp FROM MoviePlace sp " +
		"WHERE ST_Distance_Sphere(Point(cast(sp.mapX as double), cast(sp.mapY as double)), " +
		"Point(:longitude, :latitude)) <= :radius " +
		"ORDER BY ST_Distance_Sphere(Point(cast(sp.mapX as double), cast(sp.mapY as double)), " +
		"Point(:longitude, :latitude))")
	List<MoviePlace> findNearbyMoviePlaces(
		@Param("latitude") double latitude,
		@Param("longitude") double longitude,
		@Param("radius") double radius);

	@Query("SELECT COUNT(sp) FROM MoviePlace sp " +
		"WHERE ST_Distance_Sphere(Point(cast(sp.mapX as double), cast(sp.mapY as double)), " +
		"Point(:longitude, :latitude)) <= :radius")
	long countNearbyMoviePlaces(@Param("latitude") double latitude,
		@Param("longitude") double longitude,
		@Param("radius") double radius);

	// TODO : QueryDsl로 변경
}
