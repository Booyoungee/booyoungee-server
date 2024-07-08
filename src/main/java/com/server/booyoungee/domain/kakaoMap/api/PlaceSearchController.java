package com.server.booyoungee.domain.kakaoMap.api;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.booyoungee.domain.kakaoMap.application.PlaceSearchService;
import com.server.booyoungee.global.common.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/locationInfo")
@RequiredArgsConstructor
public class PlaceSearchController {

	private final PlaceSearchService placeSearchService;

	@GetMapping("/keyword")
	public ApiResponse<?> searchByKeyword(
		@RequestParam String query,
		@RequestParam(defaultValue = "1") int page,
		@RequestParam(defaultValue = "10") int size) {
		return ApiResponse.success(placeSearchService.searchByKeyword(query, page, size));
	}

	@GetMapping("/keyword/radius")
	public ApiResponse<?> searchByKeyword(
		@RequestParam String query,
		@RequestParam(defaultValue = "-9999") double x,
		@RequestParam(defaultValue = "-9999") double y,
		@RequestParam(defaultValue = "20000") int radius,
		@RequestParam(defaultValue = "1") int page,
		@RequestParam(defaultValue = "10") int size) {
		return ApiResponse.success(placeSearchService.searchByKeywordWithRadius(query, x, y, radius, page, size));
	}

	@GetMapping("/details")
	public ApiResponse<?> searchByKeyword(
		@RequestParam String query) throws IOException {
		return ApiResponse.success(placeSearchService.searchByKeywordDetails(query));
	}
}
