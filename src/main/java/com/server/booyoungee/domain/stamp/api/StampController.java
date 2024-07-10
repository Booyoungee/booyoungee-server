package com.server.booyoungee.domain.stamp.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.booyoungee.domain.stamp.application.StampService;
import com.server.booyoungee.domain.stamp.dto.StampRequestDto;
import com.server.booyoungee.global.common.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/stamp")
@RequiredArgsConstructor
public class StampController {
	private final StampService stampService;

	@PostMapping("")
	public ApiResponse<?> createStamp(@RequestBody StampRequestDto dto) {

		stampService.createStamp(dto);
		return ApiResponse.success("");

	}
}
