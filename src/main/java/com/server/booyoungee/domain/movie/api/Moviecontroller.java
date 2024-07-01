package com.server.booyoungee.domain.movie.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.booyoungee.domain.movie.application.TmdbApiService;
import com.server.booyoungee.domain.movie.dto.response.MovieDetailsDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/movies")
public class Moviecontroller {

	private final TmdbApiService tmdbApiService;

	@GetMapping("")
	public ResponseEntity<MovieDetailsDto> getMovie(@RequestParam String title) {

		return ResponseEntity.ok(tmdbApiService.getMovie(title));
	}

	@PostMapping("")
	public ResponseEntity<Void> saveMovieImages(@RequestParam String title) {
		tmdbApiService.saveMovie(title);
		return ResponseEntity.ok().build();
	}

}
