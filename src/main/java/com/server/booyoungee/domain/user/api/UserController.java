package com.server.booyoungee.domain.user.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.booyoungee.domain.user.application.UserService;
import com.server.booyoungee.domain.user.domain.User;
import com.server.booyoungee.domain.user.interceptor.UserId;
import com.server.booyoungee.global.common.ApiResponse;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
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

}
