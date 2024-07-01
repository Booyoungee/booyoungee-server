package com.server.booyoungee.domain.movie.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class MoviePosterDto {
	private String id;
	private String title;
	@JsonProperty("poster_path")
	private String posterPath;
}