package com.server.booyoungee.domain.user.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.booyoungee.domain.user.application.UserService;
import com.server.booyoungee.domain.user.domain.User;
import com.server.booyoungee.domain.user.interceptor.UserId;
import com.server.booyoungee.global.common.ApiResponse;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "User", description = "유저 관련 api / 담당자 : 이영학")
public class UserController {

	private final UserService userService;

	@PostMapping("test")
	public ApiResponse<?> getStoresByName() {
		userService.createTestUser();
		return ApiResponse.success("테스트 유저 생성");
	}

	@GetMapping("")
	public ApiResponse<?> getUserId(
		@Parameter(hidden = true) @UserId User user) {
		return ApiResponse.success(user.getUserId());
	}

	@GetMapping("/nickname")
	public ApiResponse<?> checkDuplicate(
		@RequestParam String nickname) {
		return ApiResponse.success(userService.duplicateNickname(nickname));
	}
}
