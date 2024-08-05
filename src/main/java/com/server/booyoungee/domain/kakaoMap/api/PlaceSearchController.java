package com.server.booyoungee.domain.kakaoMap.api;

import java.io.IOException;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.booyoungee.domain.kakaoMap.application.PlaceSearchService;
import com.server.booyoungee.global.common.ApiResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/locationInfo")
@RequiredArgsConstructor
@Tag(name = "PlaceSerce", description = "장소 검색 api / 담당자 : 이영학")
public class PlaceSearchController {

	private final PlaceSearchService placeSearchService;
	@Hidden
	@GetMapping("/keyword")
	public ApiResponse<?> searchByKeyword(
			@RequestParam(required = false) String query,
		@RequestParam(defaultValue = "1") int page,
		@RequestParam(defaultValue = "10") int size) {
		return ApiResponse.success(placeSearchService.searchByKeyword(query, page, size));
	}
	@Hidden
	@GetMapping("/keyword/radius")
	public ApiResponse<?> searchByKeyword(
			@RequestParam(required = false) String query,
		@RequestParam(defaultValue = "-9999") double x,
		@RequestParam(defaultValue = "-9999") double y,
		@RequestParam(defaultValue = "2000") int radius,
		@RequestParam(defaultValue = "1") int page,
		@RequestParam(defaultValue = "10") int size) {
		return ApiResponse.success(placeSearchService.searchByKeywordWithRadius(query, x, y, radius, page, size));
	}
	@Hidden
	@GetMapping("/details")
	public ApiResponse<?> searchByKeyword(
		@RequestParam String query) throws IOException {
		return ApiResponse.success(placeSearchService.searchByKeywordDetails(query));
	}
}
