package com.server.booyoungee.domain.place.api.movie;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.booyoungee.domain.place.application.movie.MoviePlaceService;
import com.server.booyoungee.domain.place.dto.response.movie.MoviePlaceResponseDto;
import com.server.booyoungee.global.common.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/movieLocation")
@Tag(name = "MovieLocation", description = "영화 촬영지 관리")
public class MoviePlaceController {
	private final MoviePlaceService moviePlaceService;

	@PostMapping("/init")
	@Operation(summary = "영화 촬영지 데이터 init")
	public ApiResponse<?> initMovieLocationData() throws Exception {
		moviePlaceService.initData();
		return ApiResponse.success(true);
	}

	@GetMapping("/{movieLocationId}")
	@Operation(summary = "영화 촬영지 조회")
	public ApiResponse<?> getMovieLocation(
		@PathVariable Long movieLocationId
	) {
		MoviePlaceResponseDto movieLocation = moviePlaceService.getMovieLocation(movieLocationId);
		return ApiResponse.success(movieLocation);
	}

	@GetMapping("")
	@Operation(summary = "영화 촬영지 페이징 조회")
	public ApiResponse<?> getMovieLocationList(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size
	) {
		Pageable pageable = PageRequest.of(page, size);
		Page<MoviePlaceResponseDto> movieLocationList = moviePlaceService.getMovieLocationList(pageable);
		return ApiResponse.success(movieLocationList);
	}

	@GetMapping("/movieName")
	@Operation(summary = "영화 촬영지 영화 이름으로 조회")
	public ApiResponse<?> getMovieLocationByMovieName(
		@RequestParam String keyword,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size
	) {
		Pageable pageable = PageRequest.of(page, size);
		Page<MoviePlaceResponseDto> movieLocationList = moviePlaceService.getMovieLocationListByMovieNameKeyword(keyword, pageable);
		return ApiResponse.success(movieLocationList);
	}

	@GetMapping("/location")
	@Operation(summary = "영화 촬영지 키워드 조회")
	public ApiResponse<?> getMovieLocationByLocation(
		@RequestParam String keyword,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size
	) {
		Pageable pageable = PageRequest.of(page, size);
		Page<MoviePlaceResponseDto> movieLocationList = moviePlaceService.getMovieLocationListByLocationKeyword(keyword, pageable);
		return ApiResponse.success(movieLocationList);
	}
}
