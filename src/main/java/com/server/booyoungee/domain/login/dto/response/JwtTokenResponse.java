package com.server.booyoungee.domain.login.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Builder
public record JwtTokenResponse(
	@NotNull
	@Schema(description = "JWT 토큰", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlh", requiredMode = REQUIRED)
	String accessToken,

	@NotNull
	@Schema(description = "JWT 토큰", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlh", requiredMode = REQUIRED)
	String refreshToken

) {
	public static JwtTokenResponse of(String accessToken, String refreshToken) {
		return JwtTokenResponse.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}
}