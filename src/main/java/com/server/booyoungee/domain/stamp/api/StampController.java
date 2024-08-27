package com.server.booyoungee.domain.stamp.api;

import static org.springframework.http.HttpStatus.*;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.server.booyoungee.domain.stamp.application.StampService;
import com.server.booyoungee.domain.stamp.dto.request.StampRequest;
import com.server.booyoungee.domain.stamp.dto.response.StampPersistResponse;
import com.server.booyoungee.domain.stamp.dto.response.StampResponse;
import com.server.booyoungee.domain.stamp.dto.response.StampResponseList;
import com.server.booyoungee.domain.user.domain.User;
import com.server.booyoungee.domain.user.interceptor.UserId;
import com.server.booyoungee.global.common.ResponseModel;
import com.server.booyoungee.global.exception.ExceptionResponse;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/stamp")
@RequiredArgsConstructor
@Tag(name = "Stamp", description = "스탬프 관련 api / 담당자 : 이영학")
public class StampController {
	private final StampService stampService;

	@Operation(summary = "스탬프 생성 요청 (본인 위치, 장소 위치)")
	@ApiResponses({
		@ApiResponse(
			responseCode = "201",
			description = "스탬프 생성 성공",
			content = @Content(schema = @Schema(implementation = StampPersistResponse.class))
		),
		@ApiResponse(
			responseCode = "409",
			description = "DUPLICATE_STAMP(중복된 요청)",
			content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
		),
		@ApiResponse(
			responseCode = "404",
			description = "NOT_FOUND_PLACE(장소를 찾을 수 없음)",
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
	public ResponseModel<StampPersistResponse> addStamp(
		@UserId User user,
		@RequestBody StampRequest dto
	) {
		StampPersistResponse response = stampService.createStamp(user, dto);
		return ResponseModel.success(response);
	}

	@Operation(
		summary = "본인이 가진 스탬프 조회",
		description = "마이페이지에서 본인이 등록한 스탬프를 모두가져옵니다.")
	@ApiResponses({
		@ApiResponse(
			responseCode = "200",
			description = "스탬프 조회 성공",
			content = @Content(schema = @Schema(implementation = StampResponseList.class))
		),
		@ApiResponse(
			responseCode = "404",
			description = "NOT_FOUND_STAMP(스탬프를 찾을 수 없음)",
			content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
		),
		@ApiResponse(
			responseCode = "400",
			description = "NOT_FOUND_TOUR_PLACE (관광지 API 오류)",
			content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
		)
	})
	@GetMapping()
	public ResponseModel<StampResponseList> getStamp(
		@UserId User user
	) {
		StampResponseList response = stampService.getStamp(user);
		return response.contents().isEmpty()
			? ResponseModel.success(NO_CONTENT, response)
			: ResponseModel.success(response);
	}

	@Operation(
		summary = "스탬프 상세 조회",
		description = "마이페이지에서 본인이 등록한 스탬프를 모두가져옵니다.")
	@ApiResponses({
		@ApiResponse(
			responseCode = "200",
			description = "스탬프 조회 성공",
			content = @Content(schema = @Schema(implementation = StampResponse.class))
		),
		@ApiResponse(
			responseCode = "404",
			description = "NOT_FOUND_STAMP(스탬프를 찾을 수 없음)",
			content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
		),
		@ApiResponse(
			responseCode = "400",
			description = "NOT_FOUND_TOUR_PLACE (관광지 API 오류)",
			content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
		)
	})
	@GetMapping("/details")
	public ResponseModel<StampResponse> getStamp(
		@RequestParam Long stampId,
		@UserId User user
	) {
		StampResponse response = stampService.getStamp(user, stampId);
		return ResponseModel.success(response);
	}

	@Operation(summary = "스탬프 삭제", description = "해당 스탬프를 삭제합니다.")
	@ApiResponses({
		@ApiResponse(
			responseCode = "200",
			description = "스탬프 삭제 성공",
			content = @Content(schema = @Schema(implementation = StampPersistResponse.class))
		),
		@ApiResponse(
			responseCode = "403",
			description = "USER_NOT_HAVE_STAMP(권한문제)",
			content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
		),
		@ApiResponse(
			responseCode = "404",
			description = "NOT_FOUND_STAMP",
			content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
		),
		@ApiResponse(
			responseCode = "400",
			description = "NOT_FOUND_TOUR_PLACE (관광지 API 오류)",
			content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
		),
	})
	@DeleteMapping()
	ResponseModel<StampPersistResponse> deleteStamp(
		@UserId User user,
		@RequestParam Long stampId
	) {
		StampPersistResponse response = stampService.deleteStamp(user, stampId);
		return ResponseModel.success(response);
	}

	@Hidden
	@Operation(summary = "특정 장소 스탬프 수 조회")
	@GetMapping("/count")
	public ResponseModel<?> getCountStampByPlaceId(
		@RequestParam Long placeId) {
		return ResponseModel.success(stampService.getStampCountByPlaceId(placeId));
	}

}
