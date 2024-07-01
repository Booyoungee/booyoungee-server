package com.server.booyoungee.domain.movie.dto.response;

import java.util.List;

import com.server.booyoungee.domain.movie.dto.request.MoviePosterDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TmdbResponseDto {
	private int page;
	private List<MoviePosterDto> results;
	private int total_pages;
	private int total_results;

	// Getters and Setters

}