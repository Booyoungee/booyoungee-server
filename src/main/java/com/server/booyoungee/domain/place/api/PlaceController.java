package com.server.booyoungee.domain.place.api;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.booyoungee.domain.place.application.PlaceService;
import com.server.booyoungee.domain.place.domain.PlaceType;
import com.server.booyoungee.domain.place.dto.response.PlaceDetailsResponse;
import com.server.booyoungee.global.common.ResponseModel;
import com.server.booyoungee.global.exception.ExceptionResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/place")
@RequiredArgsConstructor
public class PlaceController {

	private final PlaceService placeService;

	@Operation(
		summary = "장소 상세보기",
		description = "장소의 상세 정보를 조회합니다.")
	@ApiResponses({
		@ApiResponse(
			responseCode = "200",
			description = "장소 상세보기 조회",
			content = @Content(schema = @Schema(implementation = PlaceDetailsResponse.class))
		),
		@ApiResponse(
			responseCode = "404",
			description = "NOT_FOUND_MovieInfo(영화 API 오류)",
			content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
		)
	})
	@GetMapping("/details")
	public ResponseModel<PlaceDetailsResponse> getPlaceDetails(
		@RequestParam Long placeId,
		@RequestParam PlaceType placeType
	) throws IOException {
		PlaceDetailsResponse response = placeService.getDetails(placeId, placeType);
		return ResponseModel.success(response);
	}
}
