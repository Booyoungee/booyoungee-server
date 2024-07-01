package com.server.booyoungee.domain.movie.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.server.booyoungee.domain.movie.domain.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String> {
	Optional<Movie> findByTitle(String query);
}
