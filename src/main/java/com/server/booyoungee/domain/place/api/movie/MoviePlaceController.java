package com.server.booyoungee.domain.place.api.movie;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.booyoungee.domain.place.application.movie.MoviePlaceService;
import com.server.booyoungee.domain.place.domain.moviePlace.MoviePlace;
import com.server.booyoungee.domain.place.dto.response.movie.MoviePlacePageResponse;
import com.server.booyoungee.domain.place.dto.response.movie.MoviePlaceResponse;
import com.server.booyoungee.global.common.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/moviePlace")
@Tag(name = "MoviePlace", description = "영화 촬영지 장소 정보 api / 담당자 : 이한음")
public class MoviePlaceController {
	private final MoviePlaceService moviePlaceService;

	@PostMapping("/init")
	@Operation(summary = "영화 촬영지 데이터 init")
	public ApiResponse<Boolean> initMovieLocationData() throws Exception {
		moviePlaceService.initData();
		return ApiResponse.success(true);
	}

	@GetMapping("/{movieLocationId}")
	@Operation(summary = "영화 촬영지 조회")
	public ApiResponse<MoviePlaceResponse> getMovieLocation(
		@PathVariable Long movieLocationId
	) {
		MoviePlaceResponse response = moviePlaceService.getMoviePlace(movieLocationId);
		return ApiResponse.success(response);
	}

	@GetMapping("")
	@Operation(summary = "영화 촬영지 페이징 조회")
	public ApiResponse<MoviePlacePageResponse<MoviePlace>> getMovieLocationList(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size
	) {
		MoviePlacePageResponse<MoviePlace> response = moviePlaceService.getMoviePlaces(page, size);
		return ApiResponse.success(response);
	}

	@GetMapping("/movieName")
	@Operation(summary = "영화 촬영지 영화 이름으로 조회")
	public ApiResponse<MoviePlacePageResponse<MoviePlace>> getMovieLocationByMovieName(
		@RequestParam String keyword,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size
	) {
		MoviePlacePageResponse<MoviePlace> response = moviePlaceService
			.getMoviePlacesByKeyword(keyword, page, size);
		return ApiResponse.success(response);
	}

	@GetMapping("/location")
	@Operation(summary = "영화 촬영지 키워드 조회")
	public ApiResponse<MoviePlacePageResponse<MoviePlace>> getMovieLocationByLocation(
		@RequestParam String keyword,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size
	) {
		MoviePlacePageResponse<MoviePlace> response = moviePlaceService
			.getMoviePlacesByMovieNameKeyword(keyword, page, size);
		return ApiResponse.success(response);
	}
}
