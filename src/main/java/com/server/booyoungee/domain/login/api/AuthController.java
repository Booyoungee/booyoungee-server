package com.server.booyoungee.domain.login.api;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.booyoungee.domain.login.application.AuthService;
import com.server.booyoungee.domain.login.application.KakaoLoginService;
import com.server.booyoungee.domain.login.domain.Constants;
import com.server.booyoungee.domain.login.domain.enums.Provider;
import com.server.booyoungee.domain.login.dto.SocialInfoDto;
import com.server.booyoungee.domain.login.dto.request.LoginRequestDto;
import com.server.booyoungee.domain.login.dto.response.JwtTokenResponse;
import com.server.booyoungee.global.common.ApiResponse;
import com.server.booyoungee.global.exception.CustomException;
import com.server.booyoungee.global.exception.ErrorCode;
import com.server.booyoungee.global.oauth.security.info.UserAuthentication;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/oauth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;
	private final KakaoLoginService kakaoLoginService;

	@Value("${kakao.api.key}")
	private String apiKey;
	@Value("${kakao.redirect.url}")
	private String redirectUri;// Replace with your actual redirect URI

	@GetMapping("")
	public void redirectToKakaoLogin(HttpServletResponse response) throws IOException {
		String clientId = apiKey;  // Replace with your Kakao REST API Key

		String kakaoAuthUrl = "https://kauth.kakao.com/oauth/authorize"
			+ "?response_type=code"
			+ "&client_id=" + clientId
			+ "&redirect_uri=" + URLEncoder.encode(redirectUri, StandardCharsets.UTF_8.toString())
			+ "&scope=profile_nickname";

		// Set CORS headers
		response.setHeader("Access-Control-Allow-Origin", "http://localhost:8282");
		response.setHeader("Access-Control-Allow-Credentials", "true");

		// Perform the redirect
		response.sendRedirect(kakaoAuthUrl);
	}

	@GetMapping("/callback")
	public ApiResponse<?> kakaoCallback(@RequestParam String code) throws IOException {
		try {
			System.out.println("callback start");
			String accessToken = kakaoLoginService.getAccessToken(code, apiKey, redirectUri);
			LoginRequestDto request = new LoginRequestDto(Provider.KAKAO, null); // Name can be null here
			JwtTokenResponse tokens = authService.login(accessToken, request);
			return ApiResponse.success(tokens);
		} catch (Exception e) {
			return ApiResponse.error(e.getMessage());
		}
	}

	/*	@PostMapping("/login")
	public ApiResponse<JwtTokenResponse> login(
		@NotNull @RequestHeader(Constants.PROVIDER_TOKEN_HEADER) String providerToken,
		@Valid @RequestBody LoginRequestDto request) throws IOException {
		return ApiResponse.success(authService.login(providerToken, request));
	}*/

	@PostMapping("/refresh")
	public ApiResponse<JwtTokenResponse> refreshToken(
		@RequestHeader(Constants.AUTHORIZATION_HEADER) String authorizationHeader) {
		if (authorizationHeader == null || !authorizationHeader.startsWith(Constants.BEARER_PREFIX)) {
			throw new CustomException(ErrorCode.INVALID_JWT);
		}
		String refreshToken = authorizationHeader.substring(Constants.BEARER_PREFIX.length());
		System.out.println("token: " + refreshToken);
		return ApiResponse.success(authService.refresh(refreshToken));
	}

	@PostMapping("/logout")
	public ApiResponse<?> logout() {
		UserAuthentication authentication = (UserAuthentication)SecurityContextHolder.getContext().getAuthentication();
		authService.logout(Long.parseLong(authentication.getName()));
		return ApiResponse.success("로그아웃에 성공하였습니다.");
	}

	@GetMapping("/user/me")
	public SocialInfoDto getUserInfo() {
		UserAuthentication authentication = (UserAuthentication)SecurityContextHolder.getContext().getAuthentication();
		System.out.println("authentication: " + authentication.getName());
		System.out.println("token " + authentication.getAccessToken());
		return null;
		//return kakaoLoginService.getInfo(accessToken.replace("Bearer ", ""));
	}

}
