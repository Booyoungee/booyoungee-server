package com.server.booyoungee.domain.store.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.booyoungee.domain.store.application.StoreService;
import com.server.booyoungee.global.common.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
public class StoreController {
	private final StoreService storeService;

	@GetMapping("/name")
	public ApiResponse<?> getStoresByName(@RequestParam String name) {
		return ApiResponse.success(storeService.getStoreByName(name));
	}

	@GetMapping("/district")
	public ApiResponse<?> getStoresByDistrict(@RequestParam String district) {
		return ApiResponse.success(storeService.getStoreByDistrict(district));
	}

	@GetMapping("")
	public ApiResponse<?> getStoresList() {
		return ApiResponse.success(storeService.getStores());
	}
}
