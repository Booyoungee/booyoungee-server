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
import com.server.booyoungee.domain.login.dto.request.LoginRequestDto;
import com.server.booyoungee.domain.login.dto.response.JwtTokenResponse;
import com.server.booyoungee.global.annotation.UserId;
import com.server.booyoungee.global.common.ApiResponse;
import com.server.booyoungee.global.oauth.security.info.UserAuthentication;

import jakarta.servlet.http.HttpServletResponse;
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
	@Value("${kakao.redirect.url}")
	private String redirectUri;// Replace with your actual redirect URI

	@GetMapping("")
	public void redirectToKakaoLogin(HttpServletResponse response) throws IOException {
		String clientId = apiKey;  // Replace with your Kakao REST API Key

		String kakaoAuthUrl = "https://kauth.kakao.com/oauth/authorize"
			+ "?response_type=code"
			+ "&client_id=" + clientId
			+ "&redirect_uri=" + URLEncoder.encode(redirectUri, StandardCharsets.UTF_8.toString())
			+ "&response_type=code&scope=profile_nickname";
		System.out.println(kakaoAuthUrl);
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
	public ApiResponse<JwtTokenResponse> reissue(
		@NotNull @RequestHeader(Constants.AUTHORIZATION_HEADER) String authorizationHeader) {
		// Authorization 헤더에서 토큰 추출
		String refreshToken = authorizationHeader.replace("Bearer ", "");
		return ApiResponse.success(authService.refresh(refreshToken));
	}

	@PostMapping("/logout")
	public ApiResponse<?> logout(@UserId Long userId) {
		UserAuthentication authentication = (UserAuthentication)SecurityContextHolder.getContext().getAuthentication();
		authService.logout(Long.parseLong(authentication.getName()));
		return ApiResponse.success("로그아웃에 성공하였습니다.");
	}

	@GetMapping("/me")
	public ApiResponse<?> getMe(@UserId Long userId) {
		System.out.println(userId);
		UserAuthentication authentication = (UserAuthentication)SecurityContextHolder.getContext().getAuthentication();
		authService.logout(Long.parseLong(authentication.getName()));
		return ApiResponse.success("로그아웃에 성공하였습니다.");
	}
}
