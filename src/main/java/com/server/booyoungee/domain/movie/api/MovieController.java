package com.server.booyoungee.domain.movie.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.booyoungee.domain.movie.application.TmdbApiService;
import com.server.booyoungee.domain.movie.dto.response.MovieDetailsDto;
import com.server.booyoungee.global.common.ResponseModel;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/movies")
@Tag(name = "Movie", description = "영화 정보 api / 담당자 : 이영학")
public class MovieController {

	private final TmdbApiService tmdbApiService;

	@GetMapping("")
	public ResponseModel<?> getMovie(
		@RequestParam String title
	) {
		MovieDetailsDto movie = tmdbApiService.getMovie(title);
		return ResponseModel.success(movie);
	}

	@PostMapping("")
	public ResponseModel<?> saveMovieImages(@RequestParam String title) {
		tmdbApiService.saveMovie(title);
		return ResponseModel.success(true);
	}

}
