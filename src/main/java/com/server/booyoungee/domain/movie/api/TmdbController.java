package com.server.booyoungee.domain.movie.api;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
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
@Hidden
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/kmdb")
@Tag(name = "Tmdb", description = "영화 이미지 관련 api / 담당자 : 이영학")
public class TmdbController {

	private final TmdbApiService tmdbApiService;
	@Operation(summary = "영화 검색")
	@GetMapping("/title")
	public ResponseModel<TmdbResponseDto> getMovieStills(
		@RequestParam String title
	) {
		TmdbResponseDto movie = tmdbApiService.searchMoviePosterList(title);
		return ResponseModel.success(movie);

	}
	@Operation(summary = "영화 이미지 조회")
	@GetMapping("/images")
	public ResponseModel<MovieImagesDto> getMovieImages(
		@RequestParam String id
	) {
		MovieImagesDto movieImages = tmdbApiService.searchMovieImages(id);
		return ResponseModel.success(movieImages);
	}
}