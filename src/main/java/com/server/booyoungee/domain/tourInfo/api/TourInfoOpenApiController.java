package com.server.booyoungee.domain.tourInfo.api;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.booyoungee.domain.tourInfo.application.TourInfoOpenApiService;
import com.server.booyoungee.domain.tourInfo.application.TourInfoService;

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

	@Value("${tourInfo.url}")
	private String baseUrl;

	@Value("${tourInfo.key}")
	private String serviceKey;

	@Value("${tourInfo.format}")
	private String _type;

	@GetMapping("/location")
	@Operation(summary = "국문 관광정보 Open API 위치기반 검색 (mapY : 129, mapX : 35, radius : 20000(20km))")
	public ResponseEntity<?> getOpenApiInfoByLocation(
		@RequestParam(defaultValue = "10") int numOfRows,
		@RequestParam(defaultValue = "0") int pageNo,
		@RequestParam String mapX,
		@RequestParam String mapY,
		@RequestParam String radius
	) {
		String url = baseUrl
			+ "/locationBasedList1"
			+ "?ServiceKey=" + serviceKey
			+ "&numOfRows=" + numOfRows
			+ "&pageNo=" + pageNo
			+ "&MobileOS=AND"
			+ "&MobileApp=booyoungee"
			+ "&mapX=" + mapX
			+ "&mapY=" + mapY
			+ "&radius=" + radius
			+ "&_type=" + _type;

		Object jsonResult = tourInfoOpenApiService.getTourInfo(url);

		return ResponseEntity.ok(jsonResult);
	}


	@GetMapping("/keyword")
	@Operation(summary = "국문 관광정보 Open API 부산 키워드 전체 검색")
	public ResponseEntity<?> getOpenApiInfoByKeyword(
		@RequestParam(defaultValue = "10") int numOfRows,
		@RequestParam(defaultValue = "0") int pageNo,
		@RequestParam String keyword
	) {
		String encodedKeyword =  URLEncoder.encode(keyword, StandardCharsets.UTF_8);

		String url = baseUrl
			+ "/searchKeyword1"
			+ "?ServiceKey=" + serviceKey
			+ "&numOfRows=" + numOfRows
			+ "&pageNo=" + pageNo
			+ "&MobileOS=AND"
			+ "&MobileApp=booyoungee"
			+ "&keyword=" + encodedKeyword
			+ "&areaCode=" + "6" // 부산 지역코드 : 6
			+ "&_type=" + _type;

		Object jsonResult = tourInfoOpenApiService.getTourInfo(url);

		return ResponseEntity.ok(jsonResult);
	}

	@GetMapping("/festival")
	@Operation(summary = "국문 관광정보 Open API 부산 행사 정보 검색")
	public ResponseEntity<?> getOpenApiInfoByFestival(
		@RequestParam(defaultValue = "10") int numOfRows,
		@RequestParam(defaultValue = "0") int pageNo,
		@RequestParam String eventStartDate,
		@RequestParam(required = false) String eventEndDate
	) {
		String url = baseUrl
			+ "/searchFestival1"
			+ "?ServiceKey=" + serviceKey
			+ "&numOfRows=" + numOfRows
			+ "&pageNo=" + pageNo
			+ "&MobileOS=AND"
			+ "&MobileApp=booyoungee"
			+ "&eventStartDate=" + eventStartDate
			+ "&eventEndDate=" + eventEndDate
			+ "&areaCode=" + "6" // 부산 지역코드 : 6
			+ "&_type=" + _type;

		Object jsonResult = tourInfoOpenApiService.getTourInfo(url);

		return ResponseEntity.ok(jsonResult);
	}

	@GetMapping("/stay")
	@Operation(summary = "국문 관광정보 Open API 부산 숙박 정보 검색")
	public ResponseEntity<?> getOpenApiInfoByStay(
		@RequestParam(defaultValue = "10") int numOfRows,
		@RequestParam(defaultValue = "0") int pageNo
	) {
		String url = baseUrl
			+ "/searchStay1"
			+ "?ServiceKey=" + serviceKey
			+ "&numOfRows=" + numOfRows
			+ "&pageNo=" + pageNo
			+ "&MobileOS=AND"
			+ "&MobileApp=booyoungee"
			+ "&areaCode=" + "6" // 부산 지역코드 : 6
			+ "&_type=" + _type;

		Object jsonResult = tourInfoOpenApiService.getTourInfo(url);

		return ResponseEntity.ok(jsonResult);
	}

	@GetMapping("/info")
	@Operation(summary = "국문 관광정보 Open API 부산 관광 정보 검색")
	public ResponseEntity<?> getOpenApiInfoByAreaCode(
		@RequestParam(defaultValue = "10") int numOfRows,
		@RequestParam(defaultValue = "0") int pageNo
	) {
		String url = baseUrl
			+ "/areaBasedList1"
			+ "?ServiceKey=" + serviceKey
			+ "&numOfRows=" + numOfRows
			+ "&pageNo=" + pageNo
			+ "&MobileOS=AND"
			+ "&MobileApp=booyoungee"
			+ "&areaCode=" + "6" // 부산 지역코드 : 6
			+ "&_type=" + _type;

		Object jsonResult = tourInfoOpenApiService.getTourInfo(url);

		return ResponseEntity.ok(jsonResult);
	}

	@GetMapping("/detail/common")
	@Operation(summary = "국문 관광정보 Open API 콘텐츠 ID 기반 공통 정보 조회 (ex. contentId : 2786391)")
	public ResponseEntity<?> getOpenApiCommonInfoByContentId(
		@RequestParam String contentId
	) {
		String url = baseUrl
			+ "/detailCommon1"
			+ "?ServiceKey=" + serviceKey
			+ "&contentId=" + contentId
			+ "&MobileOS=AND"
			+ "&MobileApp=booyoungee"
			+ "&defaultYN=" + "Y"
			+ "&firstImageYN=" + "Y"
			+ "&areacodeYN=" + "Y"
			+ "&catcodeYN=" + "Y"
			+ "&addrinfoYN=" + "Y"
			+ "&mapinfoYN=" + "Y"
			+ "&overviewYN=" + "Y"
			+ "&_type=" + _type;

		Object jsonResult = tourInfoOpenApiService.getTourInfo(url);
		tourInfoService.viewContent(contentId);

		return ResponseEntity.ok(jsonResult);
	}

	@GetMapping("/detail/intro")
	@Operation(summary = "국문 관광정보 Open API 콘텐츠 ID 기반 소개 정보 조회 (ex. contentId : 2786391, contentTypeId : 15)")
	public ResponseEntity<?> getOpenApiIntroInfoByContentId(
		@RequestParam String contentId,
		@RequestParam String contentTypeId
	) {
		String url = baseUrl
			+ "/detailIntro1"
			+ "?ServiceKey=" + serviceKey
			+ "&contentId=" + contentId
			+ "&contentTypeId=" + contentTypeId
			+ "&MobileOS=AND"
			+ "&MobileApp=booyoungee"
			+ "&_type=" + _type;

		Object jsonResult = tourInfoOpenApiService.getTourInfo(url);
		tourInfoService.viewContent(contentId);

		return ResponseEntity.ok(jsonResult);
	}

	@GetMapping("/detail/image")
	@Operation(summary = "국문 관광정보 Open API 콘텐츠 ID 기반 이미지 정보 조회 (ex. contentId : 2786391)")
	public ResponseEntity<?> getOpenApiImageByContentId(
		@RequestParam String contentId
	) {
		String url = baseUrl
			+ "/detailImage1"
			+ "?ServiceKey=" + serviceKey
			+ "&contentId=" + contentId
			+ "&MobileOS=AND"
			+ "&MobileApp=booyoungee"
			+ "&imageYN=" + "Y"
			+ "&subImageYN=" + "Y"
			+ "&_type=" + _type;

		Object jsonResult = tourInfoOpenApiService.getTourInfo(url);
		tourInfoService.viewContent(contentId);

		return ResponseEntity.ok(jsonResult);
	}

	@GetMapping("/areaCode")
	@Operation(summary = "국문 관광정보 Open API 부산 지역 코드 조회")
	public ResponseEntity<?> getOpenApiAreaCode() {
		String url = baseUrl
			+ "/areaCode1"
			+ "?ServiceKey=" + serviceKey
			+ "&MobileOS=AND"
			+ "&MobileApp=booyoungee"
			+ "&areaCode=" + "6" // 부산 지역코드 : 6
			+ "&_type=" + _type;

		Object jsonResult = tourInfoOpenApiService.getTourInfo(url);

		return ResponseEntity.ok(jsonResult);
	}



}
