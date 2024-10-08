package com.server.booyoungee.domain.login.api;

import static org.springframework.http.HttpStatus.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.server.booyoungee.domain.login.application.AuthService;
import com.server.booyoungee.domain.login.application.KakaoLoginService;
import com.server.booyoungee.domain.login.domain.enums.Provider;
import com.server.booyoungee.domain.login.dto.request.KakaoLoginRequest;
import com.server.booyoungee.domain.login.dto.request.LoginRequest;
import com.server.booyoungee.domain.login.dto.request.NickNameRequest;
import com.server.booyoungee.domain.login.dto.request.SignUpRequest;
import com.server.booyoungee.domain.login.dto.response.JwtTokenResponse;
import com.server.booyoungee.domain.login.dto.response.SignUpResponse;
import com.server.booyoungee.domain.user.application.UserService;
import com.server.booyoungee.domain.user.domain.User;
import com.server.booyoungee.domain.user.dto.response.UserPersistResponse;
import com.server.booyoungee.domain.user.interceptor.UserId;
import com.server.booyoungee.global.common.ResponseModel;
import com.server.booyoungee.global.exception.ExceptionResponse;
import com.server.booyoungee.global.oauth.dto.KakaoTokenResponse;
import com.server.booyoungee.global.oauth.security.info.UserAuthentication;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/oauth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "카카오 로그인 api / 담당자 : 이영학")
public class AuthController {

	private final AuthService authService;
	private final KakaoLoginService kakaoLoginService;
	private final UserService userService;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${kakao.api.key}")
	private String apiKey;
	@Value("${kakao.redirect.url}")
	private String redirectUri;// Replace with your actual redirect URI

	@Operation(
		summary = "카카오 로그인",
		description = "엑세스 토큰과 리프레시 토큰을 전달 받으면 JWT 토큰을 발급합니다.")
	@ApiResponses({
		@ApiResponse(
			responseCode = "200",
			description = "로그인 성공",
			content = @Content(schema = @Schema(implementation = JwtTokenResponse.class))
		),
		@ApiResponse(
			responseCode = "400",
			description = "NOT_FOUND_USER_INFO(잘못된 엑세스 토큰)",
			content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
		),
		@ApiResponse(
			responseCode = "404",
			description = "NOT_FOUND_USER(회원가입 필요)",
			content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
		)
	})
	@PostMapping
	public ResponseModel<JwtTokenResponse> kakaoLogin(
		@RequestHeader("X-Kakao-Access-Token") String accessToken,
		@RequestHeader("X-Kakao-Refresh-Token") String refreshToken
	) throws IOException {
		LoginRequest request = new LoginRequest(Provider.KAKAO, null);
		KakaoLoginRequest kakaoLoginRequestDto = KakaoLoginRequest.of(accessToken, refreshToken);
		JwtTokenResponse tokens = authService.login(kakaoLoginRequestDto, request);
		return ResponseModel.success(tokens);
	}

	@Operation(summary = "회원가입",
		description = "엑세스 토큰과 리프레시 토큰을 , 닉네임을 전달받으면 jwt 토큰과 닉네임을 발급합니다.")
	@ApiResponses({
		@ApiResponse(
			responseCode = "201",
			description = "회원가입 성공",
			content = @Content(schema = @Schema(implementation = SignUpResponse.class))
		),
		@ApiResponse(
			responseCode = "400",
			description = "NOT_FOUND_USER_INFO(올바르지 않은 엑세스 토큰)",
			content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
		),
		@ApiResponse(
			responseCode = "409",
			description = "DUPLICATE_NICKNAME(중복된 닉네임)",
			content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
		)
	})
	@ResponseStatus(CREATED)
	@PostMapping("/signup")
	public ResponseModel<SignUpResponse> signUp(
		@RequestHeader("X-Kakao-Access-Token") String accessToken,
		@RequestHeader("X-Kakao-Refresh-Token") String refreshToken,
		@RequestBody NickNameRequest dto) {
		LoginRequest request = new LoginRequest(Provider.KAKAO, null);
		String name = userService.duplicateNickname(dto.nickname());
		SignUpRequest token = SignUpRequest.of(accessToken, refreshToken, dto.nickname());
		JwtTokenResponse tokens = authService.signup(token, name, request);
		SignUpResponse response = SignUpResponse.of(
			tokens.accessToken(), tokens.refreshToken(), name);
		return ResponseModel.success(CREATED, response);
	}

	@Operation(summary = "로그아웃", description = "로그아웃을 수행합니다.")
	@ApiResponses({
		@ApiResponse(
			responseCode = "200",
			description = "로그아웃 성공",
			content = @Content(schema = @Schema(implementation = UserPersistResponse.class))
		),
		@ApiResponse(
			responseCode = "400",
			description = "NOT_FOUND_USER(올바르지 않은 엑세스 토큰)",
			content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
		),
		@ApiResponse(
			responseCode = "401",
			description = "NOT_FOUND_USER_INFO(로그인이 필요합니다.)",
			content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
		),
		@ApiResponse(
			responseCode = "500",
			description = "서버에러",
			content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
		)
	})
	@PostMapping("/logout")
	public ResponseModel<UserPersistResponse> logout() {
		UserAuthentication authentication =
			(UserAuthentication)SecurityContextHolder.getContext().getAuthentication();
		UserPersistResponse response = authService.logout(authentication);
		return ResponseModel.success(response);
	}

	@Operation(summary = "회원탈퇴", description = "회원탈퇴를 수행합니다.")
	@ApiResponses({
		@ApiResponse(
			responseCode = "200",
			description = "회원탈퇴 성공",
			content = @Content(schema = @Schema(implementation = UserPersistResponse.class))
		),
		@ApiResponse(
			responseCode = "400",
			description = "NOT_FOUND_USER(올바르지 않은 엑세스 토큰)",
			content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
		),
		@ApiResponse(
			responseCode = "401",
			description = "NOT_FOUND_USER_INFO(로그인이 필요합니다.)",
			content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
		),
		@ApiResponse(
			responseCode = "500",
			description = "서버에러",
			content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
		)
	})
	@DeleteMapping
	public ResponseModel<UserPersistResponse> deleteUser(
		@UserId User user
	) {
		UserAuthentication authentication =
			(UserAuthentication)SecurityContextHolder.getContext().getAuthentication();
		authService.logout(authentication);
		UserPersistResponse response = authService.deleteUser(user);
		return ResponseModel.success(response);
	}

	@Operation(
		summary = "jwt 토큰 갱신",
		description = "리프레시 토큰을 전달받으면 새로운 jwt 엑세스 토큰을 발급합니다.")
	@ApiResponses({
		@ApiResponse(
			responseCode = "200",
			description = "발급 성공",
			content = @Content(schema = @Schema(implementation = JwtTokenResponse.class))
		),
		@ApiResponse(
			responseCode = "400",
			description = "NOT_FOUND_USER_INFO(잘못된 리프레시 토큰)",
			content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
		),
		@ApiResponse(
			responseCode = "401",
			description = "Unauthorized(만료된 토큰)",
			content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
		),
		@ApiResponse(
			responseCode = "404",
			description = "NOT_FOUND_USER(회원가입 필요)",
			content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
		)
	})
	@PostMapping("/refresh-jwt-token")
	public ResponseModel<JwtTokenResponse> refreshJwtToken(
		@RequestHeader("X-Kakao-Refresh-Token") String refreshToken) throws IOException {
		KakaoTokenResponse response = kakaoLoginService.refreshKakaoToken(refreshToken);
		LoginRequest request = new LoginRequest(Provider.KAKAO, null);
		KakaoLoginRequest kakaoLoginRequestDto = KakaoLoginRequest.of(
			response.getAccess_token(), response.getRefresh_token()
		);
		JwtTokenResponse tokens = authService.login(kakaoLoginRequestDto, request);
		return ResponseModel.success(tokens);
	}

	@Hidden
	@Operation(summary = "카카오 토큰 갱신",
		description = "리프레시 토큰을 전달받으면 새로운 카카오 엑세스 토큰을 발급합니다.")
	@PostMapping("/refresh-kakao-token")
	public ResponseModel<?> refreshKakaoToken(
		@RequestParam String refreshToken) throws IOException {
		KakaoTokenResponse response = kakaoLoginService.refreshKakaoToken(refreshToken);
		return ResponseModel.success(response);
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

	@Hidden
	@Operation(summary = "REST API 카카오 로그인")
	@GetMapping("/callback")
	public ResponseModel<?> kakaoCallback(@RequestParam String code) throws IOException {
		System.out.println("callback start");
		KakaoTokenResponse accessToken = kakaoLoginService.getAccessToken(code, apiKey, redirectUri);
		LoginRequest request = new LoginRequest(Provider.KAKAO, null);
		KakaoLoginRequest kakaoLoginRequestDto = KakaoLoginRequest.builder()
			.accessToken(accessToken.getAccess_token())
			.refreshToken(accessToken.getRefresh_token())
			.build();
		logger.info("Access Token: {}", accessToken.getAccess_token());
		logger.info("Refresh Token: {}", accessToken.getRefresh_token());
		JwtTokenResponse tokens = authService.login(kakaoLoginRequestDto, request);
		return ResponseModel.success(tokens);
	}

	//

}
