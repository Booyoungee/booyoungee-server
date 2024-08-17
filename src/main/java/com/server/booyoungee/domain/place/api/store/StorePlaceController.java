package com.server.booyoungee.domain.place.api.store;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.booyoungee.domain.kakaoMap.dto.response.SearchDetailDto;
import com.server.booyoungee.domain.place.application.store.StorePlaceService;
import com.server.booyoungee.domain.place.domain.storePlace.StorePlace;
import com.server.booyoungee.domain.place.dto.response.store.StorePlaceListResponse;
import com.server.booyoungee.domain.place.dto.response.store.StorePlacePageResponse;
import com.server.booyoungee.global.common.ResponseModel;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/storePlace")
@RequiredArgsConstructor
@Tag(name = "StorePlace", description = "지역 상생 식당 api / 관리자 : 이영학")
public class StorePlaceController {
	private final StorePlaceService storePlaceService;

	@GetMapping("/name")
	@Operation(summary = "식당 이름으로 조회")
	public ResponseModel<StorePlaceListResponse> getStoresByName(
		@RequestParam String name
	) {
		StorePlaceListResponse response = storePlaceService.getStoreByName(name);
		return ResponseModel.success(response);
	}

	@GetMapping("/district")
	@Operation(summary = "군/구 별 식당 조회")
	public ResponseModel<StorePlaceListResponse> getStoresByDistrict(
		@RequestParam String district
	) {
		StorePlaceListResponse response = storePlaceService.getStoreByDistrict(district);
		return ResponseModel.success(response);
	}

	@GetMapping("")
	@Operation(summary = "식당 가장 많이 조회한 순")
	public ResponseModel<StorePlacePageResponse<StorePlace>> getStoresList(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size
	) {
		StorePlacePageResponse<StorePlace> response = storePlaceService.getStores(page, size);
		return ResponseModel.success(response);
	}

	// TODO: IO 에러 로그 제거 해야함
	@GetMapping("/details")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "식당 상세 정보 조회 성공"),
		@ApiResponse(responseCode = "404", description = "식당 정보를 찾을 수 없습니다.")
	})
	@Operation(summary = "식당 상세 정보 조회")
	public ResponseModel<SearchDetailDto> getStoreDetails(@RequestParam Long storeId) throws IOException {
		SearchDetailDto response = storePlaceService.getStoreById(storeId);
		return ResponseModel.success(response);
	}

	@PostMapping("/reset/viewCount")
	@Operation(summary = "조회수 초기화")
	public ResponseModel<?> resetViews() {
		storePlaceService.restoreViews();
		return ResponseModel.success("view count를 초기화 하였습니다.");
	}

	@Hidden
	@PostMapping("update")
	public ResponseModel<?> update() {
		storePlaceService.updateXY();
		return ResponseModel.success("update");
	}

}
