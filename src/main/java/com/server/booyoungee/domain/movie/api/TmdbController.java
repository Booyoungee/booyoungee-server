package com.server.booyoungee.domain.movie.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.booyoungee.domain.movie.application.TmdbApiService;
import com.server.booyoungee.domain.movie.dto.request.MovieImagesDto;
import com.server.booyoungee.domain.movie.dto.response.TmdbResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/kmdb")
public class TmdbController {

	private final TmdbApiService tmdbApiService;

	@GetMapping("/title")
	public ResponseEntity<TmdbResponseDto> getMovieStills(@RequestParam String title) {

		return ResponseEntity.ok(tmdbApiService.searchMoviePosterList(title));

	}

	@GetMapping("/images")
	public ResponseEntity<MovieImagesDto> getMovieImages(@RequestParam String id) {

		return ResponseEntity.ok(tmdbApiService.searchMovieImages(id));

	}
}