package com.server.booyoungee.domain.movie.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.server.booyoungee.domain.movie.dto.request.MovieImagesDto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class MovieDetailsDto {
	private String id;
	private String title;
	@JsonProperty("poster_path")
	private String posterPath;
	private List<MovieImagesDto.BackDrops> backdrops;

	@Builder
	public MovieDetailsDto(String id, String title, String posterPath, List<MovieImagesDto.BackDrops> backDrops) {
		this.id = id;
		this.title = title;
		this.posterPath = "https://image.tmdb.org/t/p/w500" + posterPath;
		this.backdrops = backDrops;
	}
}
