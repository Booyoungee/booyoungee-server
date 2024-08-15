package com.server.booyoungee.global.oauth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoTokenResponse {
	private String accessToken;
	private String token_type;
	private String refreshToken;
	private Integer expires_in;
	private String scope;
	private Integer refresh_token_expires_in;

	// Getters and setters
}
