package com.server.booyoungee.domain.kakaoMap.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.booyoungee.domain.kakaoMap.application.KakaoAddressSearchService;
import com.server.booyoungee.domain.kakaoMap.dto.KakaoApiResponseDto;
import com.server.booyoungee.domain.kakaoMap.dto.KakaoKeywordResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/kakaoAddress")
@RequiredArgsConstructor
public class KakaoAddressController {
	private final KakaoAddressSearchService kakaoAddressSearchService;

	@GetMapping("/search/address")
	public KakaoApiResponseDto searchAddress(@RequestParam String query, @RequestParam(defaultValue = "1") int page,
		@RequestParam(defaultValue = "10") int size) {
		return kakaoAddressSearchService.searchAddress(query, page, size);
	}

	@GetMapping("/geo/coord2regioncode")
	public KakaoApiResponseDto getRegionCode(
		@RequestParam double x,
		@RequestParam double y) {
		return kakaoAddressSearchService.coordToRegionCode(x, y);
	}

	@GetMapping("/geo/coord2address")
	public KakaoApiResponseDto getAddress(
		@RequestParam double x,
		@RequestParam double y) {
		return kakaoAddressSearchService.coordToAddress(x, y);
	}

	@GetMapping("/search/keyword")
	public KakaoKeywordResponseDto searchByKeyword(
		@RequestParam String query,
		@RequestParam double x,
		@RequestParam double y,
		@RequestParam(defaultValue = "20000") int radius,
		@RequestParam(defaultValue = "1") int page,
		@RequestParam(defaultValue = "10") int size) {
		return kakaoAddressSearchService.searchByKeyword(query, x, y, radius, page, size);
	}
}
