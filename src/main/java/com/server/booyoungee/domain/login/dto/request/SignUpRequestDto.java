package com.server.booyoungee.domain.login.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignUpRequestDto {
	private String accessToken;
	private String refreshToken;
	private String nickname;
}
