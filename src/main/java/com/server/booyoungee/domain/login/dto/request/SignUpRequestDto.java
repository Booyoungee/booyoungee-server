package com.server.booyoungee.domain.login.dto.request;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record SignUpRequestDto(
	@JsonProperty("access_token")
	@Schema(description = "카카오 액세스 토큰", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlh", requiredMode = REQUIRED)
	@NotNull
	String accessToken,

	@JsonProperty("refresh_token")
	@Schema(description = "리프레시 토큰", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlh", requiredMode = REQUIRED)
	@NotNull
	String refreshToken,

	@Schema(description = "닉네임", example = "홍길동", requiredMode = REQUIRED)
	@NotNull
	String nickname
) {
	public static SignUpRequestDto of(String accessToken, String refreshToken, String nickname) {
		return SignUpRequestDto.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.nickname(nickname)
			.build();
	}
}
