package com.server.booyoungee.domain.login.dto.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record SignUpResponse(

	@JsonProperty("accessToken")
	@Schema(description = "JWT 토큰", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlh", requiredMode = REQUIRED)
	String accessToken,

	@JsonProperty("refreshToken")
	@Schema(description = "리프레시 토큰", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlh", requiredMode = REQUIRED)
	String refreshToken,

	@Schema(description = "닉네임", example = "홍길동", requiredMode = REQUIRED)
	String nickname

) {
	public static SignUpResponse of(String accessToken, String refreshToken, String nickname) {
		return SignUpResponse.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.nickname(nickname)
			.build();
	}
}
