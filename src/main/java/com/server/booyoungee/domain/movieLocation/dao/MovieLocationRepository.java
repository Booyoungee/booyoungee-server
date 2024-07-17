package com.server.booyoungee.domain.movieLocation.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.server.booyoungee.domain.movieLocation.domain.MovieLocation;

@Repository
public interface MovieLocationRepository extends JpaRepository<MovieLocation, Long>{
}
