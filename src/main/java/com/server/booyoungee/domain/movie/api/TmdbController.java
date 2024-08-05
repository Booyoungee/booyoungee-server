package com.server.booyoungee.domain.movie.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.booyoungee.domain.movie.application.TmdbApiService;
import com.server.booyoungee.domain.movie.dto.request.MovieImagesDto;
import com.server.booyoungee.domain.movie.dto.response.TmdbResponseDto;
import com.server.booyoungee.global.common.ResponseModel;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/kmdb")
@Tag(name = "Tmdb", description = "영화 이미지 관련 api / 담당자 : 이영학")
public class TmdbController {

	private final TmdbApiService tmdbApiService;

	@GetMapping("/title")
	public ResponseModel<?> getMovieStills(
		@RequestParam String title
	) {
		TmdbResponseDto movie = tmdbApiService.searchMoviePosterList(title);
		return ResponseModel.success(movie);

	}

	@GetMapping("/images")
	public ResponseModel<?> getMovieImages(
		@RequestParam String id
	) {
		MovieImagesDto movieImages = tmdbApiService.searchMovieImages(id);
		return ResponseModel.success(movieImages);
	}
}