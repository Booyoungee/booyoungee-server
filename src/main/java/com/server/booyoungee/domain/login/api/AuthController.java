package com.server.booyoungee.domain.login.api;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.booyoungee.domain.login.application.AuthService;
import com.server.booyoungee.domain.login.application.KakaoLoginService;
import com.server.booyoungee.domain.login.domain.Constants;
import com.server.booyoungee.domain.login.dto.request.LoginRequestDto;
import com.server.booyoungee.domain.login.dto.response.JwtTokenResponse;
import com.server.booyoungee.global.annotation.UserId;
import com.server.booyoungee.global.common.ApiResponse;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/oauth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;
	private final KakaoLoginService kakaoLoginService;

	@Value("${kakao.api.key}")
	private String apiKey;
	private final String redirectUri = "http://localhost:8282/oauth/kakao/callback"; // Replace with your actual redirect URI

	@PostMapping("")
	public void redirectToKakaoLogin(HttpServletResponse response) throws IOException {
		String clientId = apiKey;  // Replace with your Kakao REST API Key

		String kakaoAuthUrl = "https://kauth.kakao.com/oauth/authorize"
			+ "?response_type=code"
			+ "&client_id=" + clientId
			+ "&redirect_uri=" + URLEncoder.encode(redirectUri, StandardCharsets.UTF_8.toString());

		response.sendRedirect(kakaoAuthUrl);
	}

	@PostMapping("/login")
	public ApiResponse<JwtTokenResponse> login(
		@NotNull @RequestHeader(Constants.PROVIDER_TOKEN_HEADER) String providerToken,
		@Valid @RequestBody LoginRequestDto request) throws IOException {
		return ApiResponse.success(authService.login(providerToken, request));
	}

	@PostMapping("/refresh")
	public ApiResponse<JwtTokenResponse> reissue(
		@NotNull @RequestHeader(Constants.AUTHORIZATION_HEADER) String refreshToken) {
		return ApiResponse.success(authService.refresh(refreshToken));
	}

	@PostMapping("/logout")
	public ApiResponse<?> logout(@UserId Long userId) {
		authService.logout(userId);
		return ApiResponse.success("로그아웃에 성공하였습니다.");
	}
}
