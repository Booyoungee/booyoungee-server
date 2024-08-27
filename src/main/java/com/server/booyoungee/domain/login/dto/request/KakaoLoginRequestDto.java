package com.server.booyoungee.domain.login.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record KakaoLoginRequestDto(

	@JsonProperty("access_token")
	@NotBlank
	String accessToken,
	@JsonProperty("refresh_token")
	@NotBlank
	String refreshToken
) {
	public static KakaoLoginRequestDto of(String accessToken, String refreshToken) {
		return KakaoLoginRequestDto.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}
}
