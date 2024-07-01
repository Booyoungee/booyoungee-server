package com.server.booyoungee.domain.movie.domain;

import com.server.booyoungee.domain.movie.dto.request.MoviePosterDto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "movie", indexes = {
	@Index(name = "idx_movieid", columnList = "movieid"),
	@Index(name = "idx_title", columnList = "title")
})
public class Movie {
	@Id
	private String movieid;

	private String title;

	private String posterPath;

	@Lob
	private String backdrops;

	@Builder
	public Movie(MoviePosterDto dto, String backdrops) {
		this.movieid = dto.getId();
		this.title = dto.getTitle();
		this.posterPath = dto.getPosterPath();
		this.backdrops = backdrops;
	}

	public Movie() {

	}
}
