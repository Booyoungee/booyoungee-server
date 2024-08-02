package com.server.booyoungee.domain.place.dao.movie;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.server.booyoungee.domain.place.domain.moviePlace.MoviePlace;
import com.server.booyoungee.domain.place.domain.storePlace.StorePlace;

@Repository
public interface MoviePlaceRepository extends JpaRepository<MoviePlace, Long> {

	Long countByMovieNameContaining(String keyword);

	Long countByNameContaining(String keyword);

	List<MoviePlace> findAllByNameContainingOrderByViewCount(String keyword, Pageable pageable);

	List<MoviePlace> findAllByMovieNameContainingOrderByViewCount(String keyword, Pageable pageable);

	@Query("SELECT s FROM MoviePlace s ORDER BY s.viewCount DESC")
	List<MoviePlace> findAllOrderByViewCount(Pageable pageable);
	// TODO : QueryDsl로 변경
}
