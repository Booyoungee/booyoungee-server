package com.server.booyoungee.domain.store.api;

import java.io.IOException;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.booyoungee.domain.store.application.StoreService;
import com.server.booyoungee.global.common.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
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

	@Operation(summary = "조회수 랭킹")
	@GetMapping("")
	public ApiResponse<?> getStoresList(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size
	) {
		Pageable pageable = PageRequest.of(page, size);
		return ApiResponse.success(storeService.getStores(pageable));
	}

	@GetMapping("/details")
	public ApiResponse<?> getStoreDetails(@RequestParam Long storeId) throws IOException {
		return ApiResponse.success(storeService.getStoreById(storeId));
	}

	@PostMapping("/reset/views")
	public ApiResponse<?> resetViews() {
		storeService.restoreViews();
		return ApiResponse.success("views를 초기화 하였습니다.");
	}
}
