package com.server.booyoungee.domain.stamp.api;

import java.io.IOException;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.booyoungee.domain.place.domain.PlaceType;
import com.server.booyoungee.domain.stamp.application.StampService;
import com.server.booyoungee.domain.stamp.dto.StampRequestDto;
import com.server.booyoungee.domain.user.domain.User;
import com.server.booyoungee.domain.user.interceptor.UserId;
import com.server.booyoungee.global.common.ResponseModel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/stamp")
@RequiredArgsConstructor
@Tag(name = "Stamp", description = "스탬프 관련 api / 담당자 : 이영학")
public class StampController {
	private final StampService stampService;

	@Operation(summary = "스탬프 생성 요청 (본인 위치, 장소 위치)")
	@PostMapping("")
	public ResponseModel<?> createStamp(
		@Parameter(hidden = true) @UserId User user,
		@RequestBody StampRequestDto dto) {

		stampService.createStamp(user, dto);
		return ResponseModel.success("");

	}

	@Operation(summary = "본인 스탬프 조회")
	@GetMapping("")
	public ResponseModel<?> getStamp(
		@Parameter(hidden = true) @UserId User user) throws IOException {
		return ResponseModel.success(stampService.getStamp(user));
	}

	@Operation(summary = "스탬프 상세 조회")
	@GetMapping("/details/{stampId}")
	public ResponseModel<?> getStamp(
		@PathVariable Long stampId,
		@Parameter(hidden = true) @UserId User user) throws IOException {
		return ResponseModel.success(stampService.getStamp(user, stampId));
	}

	@Operation(summary = "특정 장소 스탬프 수 조회")
	@GetMapping("/count")
	public ResponseModel<?> getCountStampByPlaceId(
		@RequestParam String id) {
		return ResponseModel.success(stampService.getStampCountByPlaceId(id));
	}

	@Operation(summary = "장소 별 스탬프 수 조회")
	@GetMapping("/place-stamp-counts")
	public ResponseModel<?> getPlaceStampCounts(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size
	) {
		Pageable pageable = PageRequest.of(page, size);
		try {
			return ResponseModel.success(stampService.getPlaceStampCounts(pageable));
		} catch (IOException e) {
			return ResponseModel.error("Failed to retrieve place stamp counts");
		}
	}

	@Operation(summary = "장소 별 스탬프 수 조회")
	@GetMapping("/place-stamp-counts/type")
	public ResponseModel<?> getPlaceStampCountsByType(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size,
		@RequestParam PlaceType type
	) {
		Pageable pageable = PageRequest.of(page, size);
		try {
			return ResponseModel.success(stampService.getPlaceStampCounts(pageable, type.getKey()));
		} catch (IOException e) {
			return ResponseModel.error("Failed to retrieve place stamp counts");
		}
	}
}
