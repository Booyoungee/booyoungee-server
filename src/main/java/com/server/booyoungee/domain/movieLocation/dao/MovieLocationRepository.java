package com.server.booyoungee.domain.movieLocation.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.server.booyoungee.domain.movieLocation.domain.MovieLocation;

@Repository
public interface MovieLocationRepository extends JpaRepository<MovieLocation, Long>{
	Page<MovieLocation> findAll(Pageable pageable);

	@Query("SELECT m FROM MovieLocation m WHERE m.movieName LIKE %:keyword%")
	Page<MovieLocation> searchByMovieKeyword(String keyword, Pageable pageable);

	@Query("SELECT m FROM MovieLocation m WHERE m.locationName LIKE %:keyword% OR m.locationAddress LIKE %:keyword%")
	Page<MovieLocation> searchByMovieLocationKeyword(String keyword, Pageable pageable);
}
