package com.server.booyoungee.domain.tourInfo.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.booyoungee.domain.tourInfo.application.TourInfoService;
import com.server.booyoungee.domain.tourInfo.dto.response.TourInfoResponseDto;
import com.server.booyoungee.global.common.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tourInfo")
@Tag(name = "TourInfoApi", description = "관광 정보 관리")
public class TourInfoController {
	private final TourInfoService tourInfoService;

	@GetMapping("/{contentId}")
	@Operation(summary = "관광 정보 조회")
	public ApiResponse<?> getTourInfo(
		@PathVariable String contentId
	) {
		tourInfoService.viewContent(contentId);
		TourInfoResponseDto tourInfo = tourInfoService.getTourInfo(contentId);
		return ApiResponse.success(tourInfo);
	}

	@GetMapping("")
	@Operation(summary = "관광 정보 목록 조회")
	public ApiResponse<?> getTourInfoList() {
		List<TourInfoResponseDto> tourInfoList = tourInfoService.getTourInfoList();
		return ApiResponse.success(tourInfoList);
	}
}
