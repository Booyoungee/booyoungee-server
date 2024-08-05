package com.server.booyoungee.domain.tourInfo.api;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.booyoungee.domain.tourInfo.application.TourInfoOpenApiService;
import com.server.booyoungee.domain.tourInfo.application.TourInfoService;
import com.server.booyoungee.domain.tourInfo.dto.response.TourInfoAreaResponseDto;
import com.server.booyoungee.domain.tourInfo.dto.response.TourInfoCommonResponseDto;
import com.server.booyoungee.domain.tourInfo.dto.response.TourInfoDetailsResponseDto;
import com.server.booyoungee.domain.tourInfo.dto.response.TourInfoImageResponseDto;
import com.server.booyoungee.domain.tourInfo.dto.response.TourInfoIntroResponseDto;
import com.server.booyoungee.domain.tourInfo.dto.response.TourInfoStayResponseDto;
import com.server.booyoungee.global.common.ResponseModel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tourInfoOpenApi")
@Tag(name = "TourInfoOpenApi", description = "국문 관광정보 OpenAPI 관리 / 담당자 : 이한음")
public class TourInfoOpenApiController {
	private final TourInfoService tourInfoService;
	private final TourInfoOpenApiService tourInfoOpenApiService;

	@GetMapping("/location")
	@Operation(summary = "국문 관광정보 Open API 위치기반 검색 (mapY : 129, mapX : 35, radius : 20000(20km))")
	public ResponseModel<?> getOpenApiInfoByLocation(
		@RequestParam(defaultValue = "10") int numOfRows,
		@RequestParam(defaultValue = "0") int pageNo,
		@RequestParam String mapX,
		@RequestParam String mapY,
		@RequestParam String radius
	) throws IOException {
		List<TourInfoCommonResponseDto> jsonResult = tourInfoOpenApiService
			.getTourInfoByLocation(numOfRows, pageNo, mapX, mapY, radius);

		return ResponseModel.success(jsonResult);
	}

	@GetMapping("/keyword")
	@Operation(summary = "국문 관광정보 Open API 부산 키워드 전체 검색")
	public ResponseModel<?> getOpenApiInfoByKeyword(
		@RequestParam(defaultValue = "10") int numOfRows,
		@RequestParam(defaultValue = "0") int pageNo,
		@RequestParam String keyword
	) throws IOException {
		List<TourInfoCommonResponseDto> jsonResult = tourInfoOpenApiService
			.getTourInfoByKeyword(numOfRows, pageNo, keyword);

		return ResponseModel.success(jsonResult);
	}

	@GetMapping("/festival")
	@Operation(summary = "국문 관광정보 Open API 부산 행사 정보 검색")
	public ResponseModel<?> getOpenApiInfoByFestival(
		@RequestParam(defaultValue = "10") int numOfRows,
		@RequestParam(defaultValue = "0") int pageNo,
		@RequestParam String eventStartDate,
		@RequestParam(required = false) String eventEndDate
	) throws IOException {
		List<TourInfoCommonResponseDto> jsonResult = tourInfoOpenApiService.getTourInfoByFestival(
			numOfRows, pageNo, eventStartDate, eventEndDate);

		return ResponseModel.success(jsonResult);
	}

	@GetMapping("/stay")
	@Operation(summary = "국문 관광정보 Open API 부산 숙박 정보 검색")
	public ResponseModel<?> getOpenApiInfoByStay(
		@RequestParam(defaultValue = "10") int numOfRows,
		@RequestParam(defaultValue = "0") int pageNo
	) throws IOException {
		List<TourInfoStayResponseDto> jsonResult = tourInfoOpenApiService
			.getTourInfoByStay(numOfRows, pageNo);

		return ResponseModel.success(jsonResult);
	}

	@GetMapping("/info")
	@Operation(summary = "국문 관광정보 Open API 부산 관광 정보 검색")
	public ResponseEntity<?> getOpenApiInfoByAreaCode(
		@RequestParam(defaultValue = "10") int numOfRows,
		@RequestParam(defaultValue = "0") int pageNo
	) throws IOException {
		List<TourInfoAreaResponseDto> jsonResult = tourInfoOpenApiService
			.getTourInfoByAreaCode(numOfRows, pageNo);

		return ResponseEntity.ok(jsonResult);
	}

	@GetMapping("/detail/common")
	@Operation(summary = "국문 관광정보 Open API 콘텐츠 ID 기반 공통 정보 조회 (ex. contentId : 2786391)")
	public ResponseModel<?> getOpenApiCommonInfoByContentId(
		@RequestParam String contentId
	) throws IOException {
		List<TourInfoDetailsResponseDto> jsonResult = tourInfoOpenApiService
			.getCommonInfoByContentId(contentId);
		tourInfoService.viewContent(contentId);

		return ResponseModel.success(jsonResult);
	}

	@GetMapping("/detail/intro")
	@Operation(summary = "국문 관광정보 Open API 콘텐츠 ID 기반 소개 정보 조회 (ex. contentId : 2786391, contentTypeId : 15)")
	public ResponseModel<?> getOpenApiIntroInfoByContentId(
		@RequestParam String contentId,
		@RequestParam String contentTypeId
	) throws IOException {
		List<TourInfoIntroResponseDto> jsonResult = tourInfoOpenApiService
			.getIntroInfoByContentId(contentId, contentTypeId);
		tourInfoService.viewContent(contentId);

		return ResponseModel.success(jsonResult);
	}

	@GetMapping("/detail/image")
	@Operation(summary = "국문 관광정보 Open API 콘텐츠 ID 기반 이미지 정보 조회 (ex. contentId : 2786391)")
	public ResponseModel<?> getOpenApiImageByContentId(
		@RequestParam String contentId
	) throws IOException {
		List<TourInfoImageResponseDto> jsonResult = tourInfoOpenApiService
			.getImageInfoByContentId(contentId);
		tourInfoService.viewContent(contentId);

		return ResponseModel.success(jsonResult);
	}

	@GetMapping("/areaCode")
	@Operation(summary = "국문 관광정보 Open API 부산 지역 코드 조회")
	public ResponseModel<?> getOpenApiAreaCode() throws IOException {
		Object jsonResult = tourInfoOpenApiService.getAreaCode();

		return ResponseModel.success(jsonResult);
	}

}
