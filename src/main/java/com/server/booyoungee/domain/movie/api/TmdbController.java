package com.server.booyoungee.domain.movie.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.booyoungee.domain.movie.application.TmdbApiService;
import com.server.booyoungee.domain.movie.dto.request.MovieImagesDto;
import com.server.booyoungee.domain.movie.dto.response.TmdbResponseDto;
import com.server.booyoungee.global.common.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/kmdb")
public class TmdbController {

	private final TmdbApiService tmdbApiService;

	@GetMapping("/title")
	public ApiResponse<?> getMovieStills(
		@RequestParam String title
	) {
		TmdbResponseDto movie = tmdbApiService.searchMoviePosterList(title);
		return ApiResponse.success(movie);

	}

	@GetMapping("/images")
	public ApiResponse<?> getMovieImages(
		@RequestParam String id
	) {
		MovieImagesDto movieImages = tmdbApiService.searchMovieImages(id);
		return ApiResponse.success(movieImages);
	}
}