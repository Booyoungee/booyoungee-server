package com.server.booyoungee.domain.place.api.movie;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.booyoungee.domain.place.application.movie.MoviePlaceService;
import com.server.booyoungee.domain.place.domain.movie.MoviePlace;
import com.server.booyoungee.domain.place.dto.response.movie.MoviePlacePageResponse;
import com.server.booyoungee.domain.place.dto.response.movie.MoviePlaceResponse;
import com.server.booyoungee.global.common.ResponseModel;

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
	public ResponseModel<Boolean> initMovieLocationData() throws Exception {
		moviePlaceService.initData();
		return ResponseModel.success(true);
	}

	@GetMapping("/{movieLocationId}")
	@Operation(summary = "영화 촬영지 조회")
	public ResponseModel<MoviePlaceResponse> getMovieLocation(
		@PathVariable Long movieLocationId
	) {
		MoviePlaceResponse response = moviePlaceService.getMoviePlace(movieLocationId);
		return ResponseModel.success(response);
	}

	@GetMapping("")
	@Operation(summary = "영화 촬영지 페이징 조회")
	public ResponseModel<MoviePlacePageResponse<MoviePlace>> getMovieLocationList(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size
	) {
		MoviePlacePageResponse<MoviePlace> response = moviePlaceService.getMoviePlaces(page, size);
		return ResponseModel.success(response);
	}

	@GetMapping("/movieName")
	@Operation(summary = "영화 촬영지 영화 이름으로 조회")
	public ResponseModel<MoviePlacePageResponse<MoviePlace>> getMovieLocationByMovieName(
		@RequestParam String keyword,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size
	) {
		MoviePlacePageResponse<MoviePlace> response = moviePlaceService
			.getMoviePlacesByMovieNameKeyword(keyword, page, size);
		return ResponseModel.success(response);
	}

	@GetMapping("/location")
	@Operation(summary = "영화 촬영지 키워드 조회")
	public ResponseModel<MoviePlacePageResponse<MoviePlace>> getMovieLocationByLocation(
		@RequestParam String keyword,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size
	) {
		MoviePlacePageResponse<MoviePlace> response = moviePlaceService
			.getMoviePlacesByKeyword(keyword, page, size);
		return ResponseModel.success(response);
	}

	@GetMapping("/nearby")
	public ResponseModel<MoviePlacePageResponse<MoviePlace>>getPlacesNearby(
		@RequestParam Double mapX,
		@RequestParam Double mapY,
		@RequestParam Double radius) {

		MoviePlacePageResponse<MoviePlace> nearbyPlaces = moviePlaceService.findPlacesNearby(mapX, mapY, radius);
		return ResponseModel.success(nearbyPlaces);
	}
}
