package com.server.booyoungee.domain.login.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SignUpResponseDto {
	private String accessToken;
	private String refreshToken;
	private String nickname;
}
