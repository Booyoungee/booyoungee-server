package com.server.booyoungee.domain.tourInfo.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.booyoungee.domain.tourInfo.application.TourInfoOpenApiService;
import com.server.booyoungee.domain.tourInfo.application.TourInfoService;
import com.server.booyoungee.global.common.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tourInfoOpenApi")
@Tag(name = "TourInfoOpenApi", description = "국문 관광정보 OpenAPI 관리")
public class TourInfoOpenApiController {
	private final TourInfoService tourInfoService;
	private final TourInfoOpenApiService tourInfoOpenApiService;

	@GetMapping("/location")
	@Operation(summary = "국문 관광정보 Open API 위치기반 검색 (mapY : 129, mapX : 35, radius : 20000(20km))")
	public ApiResponse<?> getOpenApiInfoByLocation(
		@RequestParam(defaultValue = "10") int numOfRows,
		@RequestParam(defaultValue = "0") int pageNo,
		@RequestParam String mapX,
		@RequestParam String mapY,
		@RequestParam String radius
	) {
		Object jsonResult = tourInfoOpenApiService
			.getTourInfoByLocation(numOfRows, pageNo, mapX, mapY, radius);

		return ApiResponse.success(jsonResult);
	}


	@GetMapping("/keyword")
	@Operation(summary = "국문 관광정보 Open API 부산 키워드 전체 검색")
	public ApiResponse<?> getOpenApiInfoByKeyword(
		@RequestParam(defaultValue = "10") int numOfRows,
		@RequestParam(defaultValue = "0") int pageNo,
		@RequestParam String keyword
	) {
		Object jsonResult = tourInfoOpenApiService
			.getTourInfoByKeyword(numOfRows, pageNo, keyword);

		return ApiResponse.success(jsonResult);
	}

	@GetMapping("/festival")
	@Operation(summary = "국문 관광정보 Open API 부산 행사 정보 검색")
	public ApiResponse<?> getOpenApiInfoByFestival(
		@RequestParam(defaultValue = "10") int numOfRows,
		@RequestParam(defaultValue = "0") int pageNo,
		@RequestParam String eventStartDate,
		@RequestParam(required = false) String eventEndDate
	) {
		Object jsonResult = tourInfoOpenApiService.getTourInfoByFestival(
			numOfRows, pageNo, eventStartDate, eventEndDate);

		return ApiResponse.success(jsonResult);
	}

	@GetMapping("/stay")
	@Operation(summary = "국문 관광정보 Open API 부산 숙박 정보 검색")
	public ApiResponse<?> getOpenApiInfoByStay(
		@RequestParam(defaultValue = "10") int numOfRows,
		@RequestParam(defaultValue = "0") int pageNo
	) {
		Object jsonResult = tourInfoOpenApiService
			.getTourInfoByStay(numOfRows, pageNo);

		return ApiResponse.success(jsonResult);
	}

	@GetMapping("/info")
	@Operation(summary = "국문 관광정보 Open API 부산 관광 정보 검색")
	public ResponseEntity<?> getOpenApiInfoByAreaCode(
		@RequestParam(defaultValue = "10") int numOfRows,
		@RequestParam(defaultValue = "0") int pageNo
	) {
		Object jsonResult = tourInfoOpenApiService
			.getTourInfoByAreaCode(numOfRows, pageNo);

		return ResponseEntity.ok(jsonResult);
	}

	@GetMapping("/detail/common")
	@Operation(summary = "국문 관광정보 Open API 콘텐츠 ID 기반 공통 정보 조회 (ex. contentId : 2786391)")
	public ApiResponse<?> getOpenApiCommonInfoByContentId(
		@RequestParam String contentId
	) {
		Object jsonResult = tourInfoOpenApiService
			.getCommonInfoByContentId(contentId);
		tourInfoService.viewContent(contentId);

		return ApiResponse.success(jsonResult);
	}

	@GetMapping("/detail/intro")
	@Operation(summary = "국문 관광정보 Open API 콘텐츠 ID 기반 소개 정보 조회 (ex. contentId : 2786391, contentTypeId : 15)")
	public ApiResponse<?> getOpenApiIntroInfoByContentId(
		@RequestParam String contentId,
		@RequestParam String contentTypeId
	) {
		Object jsonResult = tourInfoOpenApiService
			.getIntroInfoByContentId(contentId, contentTypeId);
		tourInfoService.viewContent(contentId);

		return ApiResponse.success(jsonResult);
	}

	@GetMapping("/detail/image")
	@Operation(summary = "국문 관광정보 Open API 콘텐츠 ID 기반 이미지 정보 조회 (ex. contentId : 2786391)")
	public ApiResponse<?> getOpenApiImageByContentId(
		@RequestParam String contentId
	) {
		Object jsonResult = tourInfoOpenApiService
			.getImageInfoByContentId(contentId);
		tourInfoService.viewContent(contentId);

		return ApiResponse.success(jsonResult);
	}

	@GetMapping("/areaCode")
	@Operation(summary = "국문 관광정보 Open API 부산 지역 코드 조회")
	public ApiResponse<?> getOpenApiAreaCode() {
		Object jsonResult = tourInfoOpenApiService
			.getAreaCode();

		return ApiResponse.success(jsonResult);
	}

}
