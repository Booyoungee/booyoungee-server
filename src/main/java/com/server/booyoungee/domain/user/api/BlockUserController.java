package com.server.booyoungee.domain.user.api;

import static org.springframework.http.HttpStatus.*;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.server.booyoungee.domain.user.application.BlockService;
import com.server.booyoungee.domain.user.domain.User;
import com.server.booyoungee.domain.user.dto.response.UserPersistResponse;
import com.server.booyoungee.domain.user.interceptor.UserId;
import com.server.booyoungee.global.common.ResponseModel;
import com.server.booyoungee.global.exception.ExceptionResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/block")
@RequiredArgsConstructor
@Tag(name = "Block", description = "유저 차단 관련 api / 담당자 : 이영학")
public class BlockUserController {

	private final BlockService blockService;

	@Operation(
		summary = "유저 차단",
		description = "해당 유저를 차단하여 차단한 유저의 리뷰를 보지 않을 수 있습니다.")
	@ApiResponses({
		@ApiResponse(
			responseCode = "201",
			description = "유저 차단 성공",
			content = @Content(schema = @Schema(implementation = UserPersistResponse.class))
		),
		@ApiResponse(
			responseCode = "401",
			description = "NOT_FOUND_USER_INFO(로그인이 필요합니다.)",
			content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
		),
		@ApiResponse(
			responseCode = "404",
			description = "NOT_FOUND_USER",
			content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
		),
		@ApiResponse(
			responseCode = "409",
			description = "DUPLICATE_BLOCK_USER",
			content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
		)
	})
	@ResponseStatus(CREATED)
	@PostMapping("/{blockedUserId}")
	public ResponseModel<UserPersistResponse> blockUser(
		@UserId User user,
		@Parameter(
			description = "차단한 유저의 ID",
			example = "1",
			required = true
		)
		@PathVariable Long blockedUserId) {
		UserPersistResponse response = blockService.blockUser(user, blockedUserId);
		return ResponseModel.success(CREATED, response);
	}

	@Operation(
		summary = "유저 차단 해제",
		description = "해당 유저를 차단하여 차단한 유저의 리뷰를 보지 않을 수 있습니다.")
	@ApiResponses({
		@ApiResponse(
			responseCode = "200",
			description = "유저 차단 해제 성공",
			content = @Content(schema = @Schema(implementation = UserPersistResponse.class))
		),
		@ApiResponse(
			responseCode = "401",
			description = "NOT_FOUND_USER_INFO(로그인이 필요합니다.)",
			content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
		),
		@ApiResponse(
			responseCode = "404",
			description = "NOT_FOUND_USER",
			content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
		)
	})
	@DeleteMapping("/{blockedUserId}")
	public ResponseModel<UserPersistResponse> unblockUser(
		@UserId User user,
		@Parameter(
			description = "차단을 해제할 유저의 ID",
			example = "1",
			required = true
		)
		@PathVariable Long blockedUserId) {
		UserPersistResponse response = blockService.unblockUser(user, blockedUserId);
		return ResponseModel.success(response);
	}

}
