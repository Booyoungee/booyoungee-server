package com.server.booyoungee.domain.movieLocation.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.booyoungee.domain.movieLocation.application.MovieLocationService;
import com.server.booyoungee.global.common.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/movieLocation")
@Tag(name = "MovieLocation", description = "영화 촬영지 관리")
public class MovieLocationController {
	private final MovieLocationService movieLocationService;

	@PostMapping("/init")
	@Operation(summary = "영화 촬영지 데이터 init")
	public ApiResponse<?> initMovieLocationData() throws Exception {
		movieLocationService.initData();
		return ApiResponse.success(true);
	}
}
