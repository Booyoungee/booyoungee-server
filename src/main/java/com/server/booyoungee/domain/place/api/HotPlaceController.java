package com.server.booyoungee.domain.place.api;

import static org.springframework.http.HttpStatus.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.server.booyoungee.domain.place.application.hotPlace.HotPlaceService;
import com.server.booyoungee.domain.place.dto.response.hotPlace.HotPlaceListResponse;
import com.server.booyoungee.domain.place.dto.response.hotPlace.HotPlacePersistResponse;
import com.server.booyoungee.domain.place.dto.response.hotPlace.HotPlaceResponse;
import com.server.booyoungee.global.common.ResponseModel;
import com.server.booyoungee.global.exception.ExceptionResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/hot")
@RequiredArgsConstructor
@Tag(name = "HotPlace", description = "지금 핫한 여행지 api / 관리자 : 이영학")
public class HotPlaceController {

	private final HotPlaceService hotPlaceService;

	@Operation(
		summary = "핫한 여행지 조회",
		description = "place중 viewCount가 높은 순으로 10개를 가져옵니다.")
	@ApiResponses({
		@ApiResponse(
			responseCode = "200",
			description = "핫한 여행지 조회 성공",
			content = @Content(schema = @Schema(implementation = HotPlaceResponse.class))
		),
		@ApiResponse(
			responseCode = "400",
			description = "NOT_FOUND_TOUR_PLACE (관광지 API 오류)",
			content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
		)
	})
	@GetMapping("")
	public ResponseModel<HotPlaceListResponse> getHotPlaces() {
		HotPlaceListResponse response = hotPlaceService.getHotPlaces();
		return response.contents().isEmpty()
			? ResponseModel.success(NO_CONTENT, response)
			: ResponseModel.success(response);
	}

	@Operation(
		summary = "핫한 여행지 등록",
		description = "장소 중 조회수가 높은 순으로 10개를 등록합니다.")
	@ApiResponses({
		@ApiResponse(
			responseCode = "201",
			description = "핫한 여행지 등록 성공",
			content = @Content(schema = @Schema(implementation = HotPlacePersistResponse.class))
		),
		@ApiResponse(
			responseCode = "404",
			description = "NOT_FOUND_PLACE",
			content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
		),
		@ApiResponse(
			responseCode = "400",
			description = "NOT_FOUND_TOUR_PLACE (관광지 API 오류)",
			content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
		),
	})
	@ResponseStatus(CREATED)
	@PostMapping()
	public ResponseModel<HotPlacePersistResponse> saveHotPlace() {
		HotPlacePersistResponse response = hotPlaceService.saveHotPlace();
		return ResponseModel.success(response);
	}

}
