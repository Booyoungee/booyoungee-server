package com.server.booyoungee.domain.place.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.booyoungee.domain.place.application.hotPlace.HotPlaceService;
import com.server.booyoungee.domain.place.dto.response.hotPlace.HotPlaceResponseDto;
import com.server.booyoungee.global.common.ResponseModel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/hot")
@RequiredArgsConstructor
@Tag(name = "HotPlace", description = "지금 핫한 여행지 api / 관리자 : 이영학")
public class HotPlaceController {

	private final HotPlaceService hotPlaceService;

	@GetMapping("")
	@Operation(summary = "핫한 여행지 조회")
	public ResponseModel<List<HotPlaceResponseDto>> getHotPlaces() {
		return ResponseModel.success(hotPlaceService.getHotPlaces());
	}

	@PostMapping("")
	@Operation(summary = "핫한 여행지 저장")
	public ResponseModel<Void> saveHotPlace() {
		hotPlaceService.saveHotPlace();
		return ResponseModel.success(null);
	}

}
