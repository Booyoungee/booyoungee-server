package com.server.booyoungee.domain.stamp.api;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.booyoungee.domain.stamp.application.StampService;
import com.server.booyoungee.domain.stamp.dto.StampRequestDto;
import com.server.booyoungee.domain.user.domain.User;
import com.server.booyoungee.domain.user.interceptor.UserId;
import com.server.booyoungee.global.common.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/stamp")
@RequiredArgsConstructor
public class StampController {
	private final StampService stampService;

	@Operation(summary = "스탬프 생성 요청 (본인 위치, 장소 위치)")
	@PostMapping("")
	public ApiResponse<?> createStamp(
		@Parameter(hidden = true) @UserId User user,
		@RequestBody StampRequestDto dto) {

		stampService.createStamp(user, dto);
		return ApiResponse.success("");

	}

	@Operation(summary = "본인 스탬프 조회")
	@GetMapping("")
	public ApiResponse<?> getStamp(
		@Parameter(hidden = true) @UserId User user) throws IOException {
		return ApiResponse.success(stampService.getStamp(user));
	}

	@Operation(summary = "스탬프 상세 조회")
	@GetMapping("/details/{stampId}")
	public ApiResponse<?> getStamp(
		@PathVariable Long stampId,
		@Parameter(hidden = true) @UserId User user) throws IOException {
		return ApiResponse.success(stampService.getStamp(user, stampId));
	}

}
