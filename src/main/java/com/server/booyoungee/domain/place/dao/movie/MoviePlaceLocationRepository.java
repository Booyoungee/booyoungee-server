package com.server.booyoungee.domain.place.dao.movie;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.server.booyoungee.domain.place.domain.moviePlace.MoviePlace;

@Repository
public interface MoviePlaceLocationRepository extends JpaRepository<MoviePlace, Long> {
	Page<MoviePlace> findAll(Pageable pageable);

	@Query("SELECT m FROM MoviePlace m WHERE m.movieName LIKE %:keyword%")
	Page<MoviePlace> searchByMovieKeyword(String keyword, Pageable pageable);

	@Query("SELECT m FROM MoviePlace m WHERE m.name LIKE %:keyword% OR m.basicAddress LIKE %:keyword%")
	Page<MoviePlace> searchByMovieLocationKeyword(String keyword, Pageable pageable);
}
