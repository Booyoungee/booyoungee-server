package com.server.booyoungee.domain.store.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.booyoungee.domain.store.application.StoreService;
import com.server.booyoungee.domain.store.dto.StoreResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
public class StoreController {
	private final StoreService storeService;

	@GetMapping("/name")
	public ResponseEntity<List<StoreResponseDto>> getStoresByName(@RequestParam String name) {
		return ResponseEntity.ok(storeService.getStoreByName(name));
	}

	@GetMapping("/district")
	public ResponseEntity<List<StoreResponseDto>> getStoresByDistrict(@RequestParam String district) {
		return ResponseEntity.ok(storeService.getStoreByDistrict(district));
	}
}
