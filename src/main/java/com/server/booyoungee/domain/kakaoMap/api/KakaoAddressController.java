package com.server.booyoungee.domain.kakaoMap.api;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.booyoungee.domain.kakaoMap.application.KakaoAddressSearchService;
import com.server.booyoungee.domain.kakaoMap.dto.KakaoApiResponseDto;
import com.server.booyoungee.domain.kakaoMap.dto.KakaoKeywordResponseDto;
import com.server.booyoungee.domain.kakaoMap.dto.KakaoTransCoordResponseDto;
import com.server.booyoungee.global.common.ApiResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/kakaoAddress")
@RequiredArgsConstructor
@Tag(name = "KakaoAddress", description = "카카오 지도 관련 api / 담당자 : 이영학")
public class KakaoAddressController {
	private final KakaoAddressSearchService kakaoAddressSearchService;
	@Hidden
	@GetMapping("/search/address")
	public ApiResponse<?> searchAddress(
		@RequestParam(required = false) String query,
		@RequestParam(defaultValue = "1") int page,
		@RequestParam(defaultValue = "10") int size
	) {
		KakaoApiResponseDto kakaoApiResponseDto = kakaoAddressSearchService.searchAddress(query, page, size);
		return ApiResponse.success(kakaoApiResponseDto);
	}

	@GetMapping("/geo/coord2regioncode")
	public ApiResponse<?> getRegionCode(
		@RequestParam double x,
		@RequestParam double y
	) {
		KakaoApiResponseDto kakaoApiResponseDto = kakaoAddressSearchService.coordToRegionCode(x, y);
		return ApiResponse.success(kakaoApiResponseDto);
	}

	@GetMapping("/geo/coord2address")
	public ApiResponse<?> getAddress(
		@RequestParam double x,
		@RequestParam double y
	) {
		KakaoApiResponseDto kakaoApiResponseDto = kakaoAddressSearchService.coordToAddress(x, y);
		return ApiResponse.success(kakaoApiResponseDto);
	}

	@GetMapping("/search/keyword")
	public ApiResponse<?> searchByKeyword(@RequestParam(required = false) String query,
		@RequestParam(defaultValue = "-9999.0") double x,
		@RequestParam(defaultValue = "-9999.0") double y,
		@RequestParam(defaultValue = "20000") int radius,
		@RequestParam(defaultValue = "1") int page,
		@RequestParam(defaultValue = "10") int size
	) {
		KakaoKeywordResponseDto kakaoKeywordResponseDto = kakaoAddressSearchService.searchByKeywordWithRadius(query, x,
			y, radius, page, size);
		return ApiResponse.success(kakaoKeywordResponseDto);
	}

	@GetMapping("/geo/transcoord")
	public ApiResponse<?> transCoord(
		@RequestParam double x,
		@RequestParam double y,
		@RequestParam(defaultValue = "WGS84") String inputCoord,
		@RequestParam(defaultValue = "WGS84") String outputCoord
	) {
		KakaoTransCoordResponseDto kakaoTransCoordResponseDto = kakaoAddressSearchService.transCoord(x, y, inputCoord,
			outputCoord);
		return ApiResponse.success(kakaoTransCoordResponseDto);
	}
}
