package com.server.booyoungee.domain.place.api.movie;

import static org.springframework.http.HttpStatus.NO_CONTENT;

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

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/place/movie")
@Tag(name = "MoviePlace", description = "영화 촬영지 장소 정보 api / 담당자 : 이한음")
public class MoviePlaceController {
	private final MoviePlaceService moviePlaceService;

	@Operation(summary = "검색창에 영화 제목으로 촬영지 검색", description = "검색창에 영화 제목을 입력하여 연관된 영화 촬영지를 검색합니다.")
	@ApiResponses({
		@ApiResponse(
			responseCode = "200",
			description = "영화 제목으로 촬영지 검색 성공",
			content = @Content(schema = @Schema(implementation = MoviePlacePageResponse.class))
		),
	})
	@GetMapping
	public ResponseModel<MoviePlacePageResponse<MoviePlace>> getMovieLocationByMovieName(
		@Parameter(description = "영화 이름", example = "해운대", required = true) @RequestParam String keyword,
		@RequestParam(defaultValue = "0") @PositiveOrZero int page,
		@RequestParam(defaultValue = "10") @Positive int size
	) {
		MoviePlacePageResponse<MoviePlace> response = moviePlaceService
			.getMoviePlacesByMovieNameKeyword(keyword, page, size);
		return response.contents().isEmpty()
			? ResponseModel.success(NO_CONTENT, response)
			: ResponseModel.success(response);
	}

	@Operation(summary = "내 주변  촬영지 조회")
	@GetMapping("/nearby")
	public ResponseModel<MoviePlacePageResponse<MoviePlace>>getMoviePlacesNearby(
		@RequestParam String mapX,
		@RequestParam String mapY,
		@RequestParam String radius,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size) {

		MoviePlacePageResponse<MoviePlace> nearbyPlaces = moviePlaceService.findPlacesNearby(mapX, mapY, radius,page,size);
		return ResponseModel.success(nearbyPlaces);
	}

	@Hidden
	@PostMapping("/init")
	@Operation(summary = "영화 촬영지 데이터 init")
	public ResponseModel<Boolean> initMovieLocationData() throws Exception {
		moviePlaceService.initData();
		return ResponseModel.success(true);
	}

	@Hidden
	@GetMapping("/{movieLocationId}")
	@Operation(summary = "영화 촬영지 조회")
	public ResponseModel<MoviePlaceResponse> getMovieLocation(
		@PathVariable(name = "movieLocationId") Long movieLocationId
	) {
		MoviePlaceResponse response = moviePlaceService.getMoviePlace(movieLocationId);
		return ResponseModel.success(response);
	}

	@Hidden
	@GetMapping("/page")
	@Operation(summary = "영화 촬영지 페이징 조회")
	public ResponseModel<MoviePlacePageResponse<MoviePlace>> getMovieLocationList(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size
	) {
		MoviePlacePageResponse<MoviePlace> response = moviePlaceService.getMoviePlaces(page, size);
		return ResponseModel.success(response);
	}



	@Hidden
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
}
