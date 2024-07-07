package com.server.booyoungee.domain.movie.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.booyoungee.domain.movie.application.TmdbApiService;
import com.server.booyoungee.domain.movie.dto.response.MovieDetailsDto;
import com.server.booyoungee.global.common.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/movies")
public class Moviecontroller {

	private final TmdbApiService tmdbApiService;

	@GetMapping("")
	public ApiResponse<?> getMovie(
		@RequestParam String title
	) {
		MovieDetailsDto movie = tmdbApiService.getMovie(title);
		return ApiResponse.success(movie);
	}

	@PostMapping("")
	public ApiResponse<?> saveMovieImages(@RequestParam String title) {
		tmdbApiService.saveMovie(title);
		return ApiResponse.success(true);
	}

}
