package com.server.booyoungee.domain.login.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record KakaoLoginRequest(

	@JsonProperty("access_token")
	@NotNull
	String accessToken,

	@JsonProperty("refresh_token")
	@NotNull
	String refreshToken

) {
	public static KakaoLoginRequest of(String accessToken, String refreshToken) {
		return KakaoLoginRequest.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}
}
