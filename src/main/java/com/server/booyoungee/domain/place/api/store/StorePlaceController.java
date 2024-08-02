package com.server.booyoungee.domain.place.api.store;

import java.io.IOException;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.booyoungee.domain.place.application.store.StorePlaceService;
import com.server.booyoungee.domain.place.domain.storePlace.StorePlace;
import com.server.booyoungee.domain.place.dto.response.store.StorePlaceListResponse;
import com.server.booyoungee.domain.place.dto.response.store.StorePlacePageResponse;
import com.server.booyoungee.global.common.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/storePlace")
@RequiredArgsConstructor
@Tag(name = "StorePlace", description = "지역 상생 식당 api / 관리자 : 이영학")
public class StorePlaceController {
	private final StorePlaceService storePlaceService;

	@GetMapping("/name")
	public ApiResponse<StorePlaceListResponse> getStoresByName(
		@RequestParam String name
	) {
		StorePlaceListResponse response = storePlaceService.getStoreByName(name);
		return ApiResponse.success(response);
	}

	@GetMapping("/district")
	public ApiResponse<StorePlaceListResponse> getStoresByDistrict(
		@RequestParam String district
	) {
		StorePlaceListResponse response = storePlaceService.getStoreByDistrict(district);
		return ApiResponse.success(response);
	}

	@Operation(summary = "조회수 랭킹")
	@GetMapping("")
	public ApiResponse<StorePlacePageResponse<StorePlace>> getStoresList(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size
	) {
		StorePlacePageResponse<StorePlace> response = storePlaceService.getStores(page, size);
		return ApiResponse.success(response);
	}

	@GetMapping("/details")
	public ApiResponse<?> getStoreDetails(@RequestParam Long storeId) throws IOException {
		return ApiResponse.success(storePlaceService.getStoreById(storeId));
	}

	@PostMapping("/reset/viewCount")
	public ApiResponse<?> resetViews() {
		storePlaceService.restoreViews();
		return ApiResponse.success("view count를 초기화 하였습니다.");
	}
}
