package com.server.booyoungee.domain.place.api.tour;

import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.io.IOException;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.booyoungee.domain.place.application.tour.TourPlaceService;
import com.server.booyoungee.domain.place.domain.tour.TourContentType;
import com.server.booyoungee.domain.place.dto.response.PlaceSummaryListResponse;
import com.server.booyoungee.domain.place.dto.response.tour.TourInfoDetailsResponseDto;
import com.server.booyoungee.domain.place.dto.response.tour.TourPlaceResponseDto;
import com.server.booyoungee.global.common.ResponseModel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/place/tour")
@Tag(name = "TourInfoApi", description = "관광 정보 조회 api / 담당자 : 이한음")
public class TourPlaceController {
	private final TourPlaceService tourPlaceService;

	@Operation(summary = "관광지 카테고리 필터 조회", description = "관광지 카테고리에서 별점, 리뷰, 좋아요 순 필터로 조회합니다.")
	@ApiResponses({
		@ApiResponse(
			responseCode = "200",
			description = "관광지 카테고리 필터 조회 성공",
			content = @Content(schema = @Schema(implementation = PlaceSummaryListResponse.class))
		),
	})
	@GetMapping
	public ResponseModel<PlaceSummaryListResponse> getTourPlacesByFilter(
		@Parameter(description = "필터(star/review/like)", example = "star", required = true) @RequestParam String filter
	) {
		PlaceSummaryListResponse response = tourPlaceService.getTourPlacesByFilter(filter);
		return response.contents().isEmpty()
			? ResponseModel.success(NO_CONTENT, response)
			: ResponseModel.success(response);
	}

	@GetMapping("/{contentId}")
	@Operation(summary = "관광 정보 조회")
	public ResponseModel<?> getTourInfo(
		@PathVariable String contentId
	) throws IOException {
		//tourPlaceService.viewContent(contentId);
		TourInfoDetailsResponseDto tourInfo = tourPlaceService.getTour(Long.valueOf(contentId));
		return ResponseModel.success(tourInfo);
	}

	@GetMapping("/list")
	@Operation(summary = "관광 정보 목록 조회")
	public ResponseModel<?> getTourInfoList() {
		List<TourPlaceResponseDto> tourInfoList = tourPlaceService.getTourInfoList();
		return ResponseModel.success(tourInfoList);
	}

	@GetMapping("/type")
	@Operation(summary = "관광 정보 목록 카테고리 조회",
		description = "지정된 카테고리 타입에 따른 관광 정보 목록을 조회합니다.\n\n"
			+ "Available TourContentType values:\n"
			+ "- TOURIST_SPOT: (관광지)\n"
			+ "- CULTURAL_FACILITY: (문화시설)\n"
			+ "- EVENT_PERFORMANCE_FESTIVAL: (행사/공연/축제)\n"
			+ "- TRAVEL_COURSE: (여행코스)\n"
			+ "- LEPORTS: (레포츠)\n"
			+ "- ACCOMMODATION: (숙박)\n"
			+ "- SHOPPING: (쇼핑)\n"
			+ "- RESTAURANT: (음식점)"
	)
	public ResponseModel<?> getTourInfoListByType(@RequestParam TourContentType contentId) {
		List<TourPlaceResponseDto> tourInfoList = tourPlaceService.getTourInfoListByType(contentId);
		return ResponseModel.success(tourInfoList);
	}

}
