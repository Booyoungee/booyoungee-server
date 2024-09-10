package com.server.booyoungee.domain.place.api.store;

import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.booyoungee.domain.kakaoMap.dto.response.SearchDetailDto;
import com.server.booyoungee.domain.place.application.store.StorePlaceService;
import com.server.booyoungee.domain.place.domain.store.StorePlace;
import com.server.booyoungee.domain.place.dto.response.PlaceSummaryListResponse;
import com.server.booyoungee.domain.place.dto.response.store.StorePlaceListResponse;
import com.server.booyoungee.domain.place.dto.response.store.StorePlacePageResponse;
import com.server.booyoungee.global.common.ResponseModel;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/place/store")
@RequiredArgsConstructor
@Tag(name = "StorePlace", description = "지역 상생 식당 api / 담당자 : 이한음")
public class StorePlaceController {
	private final StorePlaceService storePlaceService;

	@Operation(summary = "지역 상생 식당 카테고리 필터 조회", description = "지역 상생 식당 카테고리에서 별점, 리뷰, 좋아요 순 필터로 조회합니다.")
	@ApiResponses({
		@ApiResponse(
			responseCode = "200",
			description = "지역 상생 식당 카테고리 필터 조회 성공",
			content = @Content(schema = @Schema(implementation = PlaceSummaryListResponse.class))
		),
	})
	@GetMapping
	public ResponseModel<PlaceSummaryListResponse> getStorePlacesByFilter(
		@Parameter(description = "필터(star/review/like)", example = "star", required = true) @RequestParam String filter
	) {
		PlaceSummaryListResponse response = storePlaceService.getStorePlacesByFilter(filter);
		return response.contents().isEmpty()
			? ResponseModel.success(NO_CONTENT, response)
			: ResponseModel.success(response);
	}

	@Operation(summary = "내 주변 지역 상생 식당 지도 마커", description = "내 위치를 기반으로 주변 지역 상생 식당 마커를 조회합니다.")
	@ApiResponses({
		@ApiResponse(
			responseCode = "200",
			description = "내 주변 지역 상생 식당 지도 마커 불러오기 성공",
			content = @Content(schema = @Schema(implementation = StorePlaceListResponse.class))
		),
	})
	@GetMapping("/nearby")
	public ResponseModel<StorePlaceListResponse> getStorePlacesNearby(
		@Parameter(description = "내 위치 X 좌표", example = "129", required = true) @RequestParam String mapX,
		@Parameter(description = "내 위치 Y 좌표", example = "35", required = true) @RequestParam String mapY,
		@Parameter(description = "내 위치 기준 반경", example = "20000", required = true) @RequestParam int radius
	) {
		StorePlaceListResponse response = storePlaceService.getStorePlacesNearby(mapX, mapY, radius);
		return response.contents().isEmpty()
			? ResponseModel.success(NO_CONTENT, response)
			: ResponseModel.success(response);
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

	@Hidden
	@GetMapping("/name")
	@Operation(summary = "식당 이름으로 조회")
	public ResponseModel<StorePlaceListResponse> getStoresByName(
		@RequestParam String name
	) {
		StorePlaceListResponse response = storePlaceService.getStoreByName(name);
		return ResponseModel.success(response);
	}

	@Hidden
	@GetMapping("/district")
	@Operation(summary = "군/구 별 식당 조회")
	public ResponseModel<StorePlaceListResponse> getStoresByDistrict(
		@RequestParam String district
	) {
		StorePlaceListResponse response = storePlaceService.getStoreByDistrict(district);
		return ResponseModel.success(response);
	}

	@Hidden
	@GetMapping("/viewCount")
	@Operation(summary = "식당 가장 많이 조회한 순")
	public ResponseModel<StorePlacePageResponse<StorePlace>> getStoresList(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size
	) {
		StorePlacePageResponse<StorePlace> response = storePlaceService.getStores(page, size);
		return ResponseModel.success(response);
	}

	@Hidden
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
