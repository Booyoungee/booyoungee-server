package com.server.booyoungee.domain.kakaoMap.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.booyoungee.domain.kakaoMap.application.PlaceSearchService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/locationInfo")
@RequiredArgsConstructor
public class PlaceSearchController {

	private final PlaceSearchService placeSearchService;

	@GetMapping("/keyword")
	public ResponseEntity<?> searchByKeyword(
		@RequestParam String query,
		@RequestParam(defaultValue = "1") int page,
		@RequestParam(defaultValue = "10") int size) {
		return ResponseEntity.ok(placeSearchService.searchByKeyword(query, page, size));
	}

	@GetMapping("/keyword/radius")
	public ResponseEntity<?> searchByKeyword(
		@RequestParam String query,
		@RequestParam(defaultValue = "-9999") double x,
		@RequestParam(defaultValue = "-9999") double y,
		@RequestParam(defaultValue = "20000") int radius,
		@RequestParam(defaultValue = "1") int page,
		@RequestParam(defaultValue = "10") int size) {
		return ResponseEntity.ok(placeSearchService.searchByKeywordWithRadius(query, x, y, radius, page, size));
	}

	@GetMapping("/details")
	public ResponseEntity<?> searchByKeyword(
		@RequestParam String query) {
		return ResponseEntity.ok(placeSearchService.searchByKeywordDetails(query));
	}
}
