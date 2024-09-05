package com.server.booyoungee.domain.user.api;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.booyoungee.domain.user.application.UserService;
import com.server.booyoungee.domain.user.domain.User;
import com.server.booyoungee.domain.user.dto.response.NickNameResponse;
import com.server.booyoungee.domain.user.dto.response.UserPersistResponse;
import com.server.booyoungee.domain.user.dto.response.UserResponse;
import com.server.booyoungee.domain.user.interceptor.UserId;
import com.server.booyoungee.global.common.ResponseModel;
import com.server.booyoungee.global.exception.ExceptionResponse;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Tag(name = "User", description = "유저 관련 api / 담당자 : 이영학")
public class UserController {

	private final UserService userService;

	@Hidden
	@GetMapping("")
	public ResponseModel<?> getUserId(
		@UserId User user) {
		return ResponseModel.success(user.getUserId());
	}

	@Operation(summary = "유저 정보 조회")
	@ApiResponses({
		@ApiResponse(
			responseCode = "200",
			description = "유저 ID 및 이름 조회",
			content = @Content(schema = @Schema(implementation = UserResponse.class))
		),
		@ApiResponse(
			responseCode = "401",
			description = "NOT_FOUND_USER_INFO(로그인이 필요합니다.)",
			content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
		)
	})
	@GetMapping("/me")
	public ResponseModel<UserResponse> getUser(
		@UserId User user
	) {
		UserResponse response = UserResponse.of(user.getUserId(), user.getName());
		return ResponseModel.success(response);
	}

	@Operation(summary = "닉네임 중복 확인")
	@ApiResponses({
		@ApiResponse(
			responseCode = "200",
			description = "중복 없음",
			content = @Content(schema = @Schema(implementation = NickNameResponse.class))
		),
		@ApiResponse(
			responseCode = "401",
			description = "NOT_FOUND_USER_INFO(로그인이 필요합니다.)",
			content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
		),
		@ApiResponse(
			responseCode = "404",
			description = "NOT_FOUND_USER(올바르지 않은 엑세스 토큰)",
			content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
		),
		@ApiResponse(
			responseCode = "409",
			description = "DUPLICATE_NICKNAME(중복된 닉네임)",
			content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
		)
	})
	@GetMapping("/nickname")
	public ResponseModel<NickNameResponse> checkDuplicate(
		@Parameter(
			description = "변경할 닉네임",
			example = "홍길동",
			required = true
		)
		@RequestParam String nickname) {
		String name = userService.duplicateNickname(nickname);
		NickNameResponse response = NickNameResponse.of(name);
		return ResponseModel.success(response);
	}

	@Operation(summary = "닉네임 변경")
	@ApiResponses({
		@ApiResponse(
			responseCode = "201",
			description = "닉네임변경",
			content = @Content(schema = @Schema(implementation = NickNameResponse.class))
		),
		@ApiResponse(
			responseCode = "401",
			description = "NOT_FOUND_USER_INFO(로그인이 필요합니다.)",
			content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
		),
		@ApiResponse(
			responseCode = "404",
			description = "NOT_FOUND_USER(올바르지 않은 엑세스 토큰)",
			content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
		),
		@ApiResponse(
			responseCode = "409",
			description = "DUPLICATE_NICKNAME(중복된 닉네임)",
			content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
		)
	})
	@PatchMapping("/nickname")
	public ResponseModel<NickNameResponse> updateNickname(
		@UserId User user,
		@Parameter(
			description = "변경할 닉네임",
			example = "홍길동",
			required = true
		)
		@RequestParam String nickname) {
		String name = userService.duplicateNickname(nickname);
		NickNameResponse response = NickNameResponse.of(
			userService.updateNickname(user, name));
		return ResponseModel.success(response);
	}

	@Hidden
	@DeleteMapping
	public ResponseModel<UserPersistResponse> deleteUser(
		@UserId User user) {
		UserPersistResponse response = userService.deleteUser(user);
		return ResponseModel.success(response);
	}
}
