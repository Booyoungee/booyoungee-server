package com.server.booyoungee.domain.place.dao.movie;

import java.util.List;

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

	@Query("SELECT s FROM MoviePlace s ORDER BY s.viewCount DESC")
	List<MoviePlace> findAllOrderByViewCount(Pageable pageable);

	@Query("SELECT s FROM MoviePlace s where s.viewCount>0 ORDER BY s.viewCount DESC")
	List<MoviePlace> top10MoviePlace(Pageable pageable);

	@Query("SELECT m FROM MoviePlace m WHERE " +
		"SQRT(POWER((CAST(m.mapX AS double) - :mapX), 2) + POWER((CAST(m.mapY AS double) - :mapY), 2)) <= :radius")
	List<MoviePlace> findPlacesNearby(@Param("mapX") double mapX, @Param("mapY") double mapY,
		@Param("radius") double radius);

	// TODO : QueryDsl로 변경
}
