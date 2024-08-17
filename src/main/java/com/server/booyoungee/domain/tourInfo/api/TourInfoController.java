package com.server.booyoungee.domain.tourInfo.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.booyoungee.domain.tourInfo.application.TourInfoService;
import com.server.booyoungee.domain.tourInfo.domain.etc.TourContentType;
import com.server.booyoungee.domain.tourInfo.dto.response.TourInfoResponseDto;
import com.server.booyoungee.global.common.ResponseModel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tourInfo")
@Tag(name = "TourInfoApi", description = "관광 정보 조회 api / 담당자 : 이한음")
public class TourInfoController {
	private final TourInfoService tourInfoService;

	@GetMapping("/{contentId}")
	@Operation(summary = "관광 정보 조회")
	public ResponseModel<?> getTourInfo(
		@PathVariable String contentId
	) {
		tourInfoService.viewContent(contentId);
		TourInfoResponseDto tourInfo = tourInfoService.getTourInfo(contentId);
		return ResponseModel.success(tourInfo);
	}

	@GetMapping("")
	@Operation(summary = "관광 정보 목록 조회")
	public ResponseModel<?> getTourInfoList() {
		List<TourInfoResponseDto> tourInfoList = tourInfoService.getTourInfoList();
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
		List<TourInfoResponseDto> tourInfoList = tourInfoService.getTourInfoListByType(contentId);
		return ResponseModel.success(tourInfoList);
	}
}
