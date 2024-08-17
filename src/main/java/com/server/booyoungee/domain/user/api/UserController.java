package com.server.booyoungee.domain.user.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.booyoungee.domain.user.application.UserService;
import com.server.booyoungee.domain.user.domain.User;
import com.server.booyoungee.domain.user.dto.response.UserResponse;
import com.server.booyoungee.domain.user.interceptor.UserId;
import com.server.booyoungee.global.common.ResponseModel;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
		@Parameter(hidden = true) @UserId User user) {
		return ResponseModel.success(user.getUserId());
	}

	@Operation(summary = "유저 정보 조회")
	@GetMapping("/me")
	public ResponseModel<UserResponse> getUser(
		@Parameter(hidden = true) @UserId User user
	) {
		UserResponse response = UserResponse.of(user.getUserId(), user.getName());
		return ResponseModel.success(response);
	}

	@Operation(summary = "닉네임 중복 확인")
	@GetMapping("/nickname")
	public ResponseModel<?> checkDuplicate(
		@RequestParam String nickname) {
		return ResponseModel.success(userService.duplicateNickname(nickname));
	}

	@Operation(summary = "닉네임 변경")
	@PutMapping("/nickname")
	public ResponseModel<?> updateNickname(
		@Parameter(hidden = true) @UserId User user,
		@RequestParam String nickname) {
		return ResponseModel.success(userService.updateNickname(user, nickname));
	}
}
