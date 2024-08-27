package com.server.booyoungee.domain.place.api.tour;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.booyoungee.domain.place.application.tour.TourInfoOpenApiService;
import com.server.booyoungee.domain.place.application.tour.TourPlaceService;
import com.server.booyoungee.domain.place.dto.response.tour.TourInfoAreaResponseDto;
import com.server.booyoungee.domain.place.dto.response.tour.TourInfoCommonResponse;
import com.server.booyoungee.domain.place.dto.response.tour.TourInfoDetailsResponseDto;
import com.server.booyoungee.domain.place.dto.response.tour.TourInfoImageResponseDto;
import com.server.booyoungee.domain.place.dto.response.tour.TourInfoIntroResponseDto;
import com.server.booyoungee.domain.place.dto.response.tour.TourInfoStayResponseDto;
import com.server.booyoungee.global.common.ResponseModel;
import com.server.booyoungee.global.exception.ExceptionResponse;

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
@RequestMapping("/api/v1/tourInfoOpenApi")
@Tag(name = "TourInfoOpenApi", description = "국문 관광정보 OpenAPI 관리 / 담당자 : 이한음")
public class TourInfoOpenApiController {
	private final TourPlaceService tourPlaceService;
	private final TourInfoOpenApiService tourInfoOpenApiService;

	@Operation(summary = "국문 관광 정보 Open API 위치기반 검색 ", description = "위치를 기반으로 부산 시내의 관광지를 검색합니다."
		+ "(mapX : 129, mapY : 35, radius : 20000(20km))")
	@ApiResponses({
		@ApiResponse(
			responseCode = "200",
			description = "위치 기반 관광 정보 검색 성공",
			content = @Content(schema = @Schema(implementation = TourInfoCommonResponse.class))
		),
		@ApiResponse(
			responseCode = "400",
			description = "OPEN_API_CALL_ERROR / LIST_PARSING_ERROR",
			content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
		),
	})
	@GetMapping("/location")
	public ResponseModel<List<TourInfoCommonResponse>> getOpenApiInfoByLocation(
		@Parameter(description = "페이지 내 최대 응답 개수", example = "10", required = true) @RequestParam @Positive int numOfRows,
		@Parameter(description = "페이지 인덱스", example = "0", required = true) @RequestParam @PositiveOrZero int pageNo,
		@Parameter(description = "GPS X좌표", example = "129", required = true) @RequestParam String mapX,
		@Parameter(description = "GPS Y좌표", example = "35", required = true) @RequestParam String mapY,
		@Parameter(description = "반경 거리", example = "20000", required = true) @RequestParam @Positive String radius
	) {
		List<TourInfoCommonResponse> jsonResult = tourInfoOpenApiService
			.getTourInfoByLocation(numOfRows, pageNo, mapX, mapY, radius);
		tourPlaceService.viewContents(jsonResult);
		return ResponseModel.success(jsonResult);
	}

	@Operation(summary = "국문 관광정보 Open API 부산 키워드 전체 검색")
	@GetMapping("/keyword")
	public ResponseModel<List<TourInfoCommonResponse>> getOpenApiInfoByKeyword(
		@RequestParam(defaultValue = "10") int numOfRows,
		@RequestParam(defaultValue = "0") int pageNo,
		@RequestParam String keyword
	) {
		List<TourInfoCommonResponse> jsonResult = tourInfoOpenApiService
			.getTourInfoByKeyword(numOfRows, pageNo, keyword);
		return ResponseModel.success(jsonResult);
	}

	@Hidden
	@GetMapping("/festival")
	@Operation(summary = "국문 관광정보 Open API 부산 행사 정보 검색")
	public ResponseModel<?> getOpenApiInfoByFestival(
		@RequestParam(defaultValue = "10") int numOfRows,
		@RequestParam(defaultValue = "0") int pageNo,
		@RequestParam String eventStartDate,
		@RequestParam(required = false) String eventEndDate
	) {
		List<TourInfoCommonResponse> jsonResult = tourInfoOpenApiService.getTourInfoByFestival(
			numOfRows, pageNo, eventStartDate, eventEndDate);
		return ResponseModel.success(jsonResult);
	}

	@Hidden
	@GetMapping("/stay")
	@Operation(summary = "국문 관광정보 Open API 부산 숙박 정보 검색")
	public ResponseModel<?> getOpenApiInfoByStay(
		@RequestParam(defaultValue = "10") int numOfRows,
		@RequestParam(defaultValue = "0") int pageNo
	) {
		List<TourInfoStayResponseDto> jsonResult = tourInfoOpenApiService
			.getTourInfoByStay(numOfRows, pageNo);
		return ResponseModel.success(jsonResult);
	}

	@Hidden
	@GetMapping("/info")
	@Operation(summary = "국문 관광정보 Open API 부산 관광 정보 검색")
	public ResponseEntity<?> getOpenApiInfoByAreaCode(
		@RequestParam(defaultValue = "10") int numOfRows,
		@RequestParam(defaultValue = "0") int pageNo
	) {
		List<TourInfoAreaResponseDto> jsonResult = tourInfoOpenApiService
			.getTourInfoByAreaCode(numOfRows, pageNo);
		return ResponseEntity.ok(jsonResult);
	}

	@Hidden
	@GetMapping("/detail/common")
	@Operation(summary = "국문 관광정보 Open API 콘텐츠 ID 기반 상세 정보 조회 (ex. contentId : 2786391)")
	public ResponseModel<List<TourInfoDetailsResponseDto>> getOpenApiCommonInfoByContentId(
		@RequestParam String contentId
	) {
		List<TourInfoDetailsResponseDto> jsonResult = tourInfoOpenApiService
			.getCommonInfoByContentId(contentId);
		tourPlaceService.viewContent(contentId, jsonResult.get(0).contenttypeid());
		return ResponseModel.success(jsonResult);
	}

	@Hidden
	@GetMapping("/detail/intro")
	@Operation(summary = "국문 관광정보 Open API 콘텐츠 ID 기반 소개 정보 조회 (ex. contentId : 2786391, contentTypeId : 15)")
	public ResponseModel<?> getOpenApiIntroInfoByContentId(
		@RequestParam String contentId,
		@RequestParam String contentTypeId
	) {
		List<TourInfoIntroResponseDto> jsonResult = tourInfoOpenApiService
			.getIntroInfoByContentId(contentId, contentTypeId);
		tourPlaceService.viewContent(contentId, contentTypeId);
		return ResponseModel.success(jsonResult);
	}

	@Hidden
	@GetMapping("/detail/image")
	@Operation(summary = "국문 관광정보 Open API 콘텐츠 ID 기반 이미지 정보 조회 (ex. contentId : 2786391)")
	public ResponseModel<?> getOpenApiImageByContentId(
		@RequestParam String contentId
	) {
		List<TourInfoImageResponseDto> jsonResult = tourInfoOpenApiService
			.getImageInfoByContentId(contentId);
		// tourPlaceService.viewContent(contentId);
		return ResponseModel.success(jsonResult);
	}

	@GetMapping("/areaCode")
	@Operation(summary = "국문 관광정보 Open API 부산 지역 코드 조회")
	public ResponseModel<?> getOpenApiAreaCode() {
		Object jsonResult = tourInfoOpenApiService.getAreaCode();
		return ResponseModel.success(jsonResult);
	}

}
