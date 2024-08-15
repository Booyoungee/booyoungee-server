package com.server.booyoungee.domain.login.api;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.booyoungee.domain.login.application.AuthService;
import com.server.booyoungee.domain.login.application.KakaoLoginService;
import com.server.booyoungee.domain.login.domain.enums.Provider;
import com.server.booyoungee.domain.login.dto.request.LoginRequestDto;
import com.server.booyoungee.domain.login.dto.request.SignUpRequestDto;
import com.server.booyoungee.domain.login.dto.response.JwtTokenResponse;
import com.server.booyoungee.domain.login.dto.response.SignUpResponseDto;
import com.server.booyoungee.domain.user.application.UserService;
import com.server.booyoungee.global.common.ResponseModel;
import com.server.booyoungee.global.oauth.dto.KakaoTokenResponse;
import com.server.booyoungee.global.oauth.security.info.UserAuthentication;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/oauth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "카카오 로그인 api / 담당자 : 이영학")
public class AuthController {

	private final AuthService authService;
	private final KakaoLoginService kakaoLoginService;
	private final UserService userService;

	@Value("${kakao.api.key}")
	private String apiKey;
	@Value("${kakao.redirect.url}")
	private String redirectUri;// Replace with your actual redirect URI

	@GetMapping("")
	public ResponseModel<?> kakaoLogin(@RequestBody KakaoTokenResponse accessToken) throws IOException {
		try {
			LoginRequestDto request = new LoginRequestDto(Provider.KAKAO, null); // Name can be null here
			JwtTokenResponse tokens = authService.login(accessToken, request);
			return ResponseModel.success(tokens);
		} catch (Exception e) {
			return ResponseModel.error(e.getMessage());
		}
	}

	@GetMapping("/signup")
	public ResponseModel<?> signUp(@RequestBody SignUpRequestDto token) throws IOException {
		try {
			LoginRequestDto request = new LoginRequestDto(Provider.KAKAO, null);
			String name = userService.duplicateNickname(token.getNickname());
			JwtTokenResponse tokens = authService.signup(token, name, request);
			SignUpResponseDto dto = SignUpResponseDto.builder()
				.accessToken(tokens.accessToken())
				.refreshToken(tokens.refreshToken())
				.nickname(name)
				.build();
			return ResponseModel.success(dto);
		} catch (Exception e) {
			return ResponseModel.error(e.getMessage());
		}
	}

	@PostMapping("/logout")
	public ResponseModel<?> logout() {
		UserAuthentication authentication = (UserAuthentication)SecurityContextHolder.getContext().getAuthentication();
		authService.logout(authentication);
		return ResponseModel.success("로그아웃에 성공하였습니다.");
	}

	@PostMapping("/refresh-kakao-token")
	public ResponseModel<?> refreshKakaoToken(@RequestParam String refreshToken) throws IOException {
		KakaoTokenResponse response = kakaoLoginService.refreshKakaoToken(refreshToken);
		LoginRequestDto request = new LoginRequestDto(Provider.KAKAO, null);
		JwtTokenResponse tokens = authService.login(response, request);
		return ResponseModel.success((tokens));
	}

	@Operation(summary = "REST API 카카오 로그인")
	@Hidden
	@GetMapping("/login")
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

	@Operation(summary = "REST API 카카오 로그인")
	@Hidden
	@GetMapping("/callback")
	public ResponseModel<?> kakaoCallback(@RequestParam String code) throws IOException {
		try {
			System.out.println("callback start");
			KakaoTokenResponse accessToken = kakaoLoginService.getAccessToken(code, apiKey, redirectUri);
			LoginRequestDto request = new LoginRequestDto(Provider.KAKAO, null); // Name can be null here
			JwtTokenResponse tokens = authService.login(accessToken, request);
			return ResponseModel.success(tokens);
		} catch (Exception e) {
			return ResponseModel.error(e.getMessage());
		}
	}

}
