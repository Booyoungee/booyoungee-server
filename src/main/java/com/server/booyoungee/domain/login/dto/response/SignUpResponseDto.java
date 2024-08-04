package com.server.booyoungee.domain.login.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignUpResponseDto {
	private String accesstoken;
	private String refreshToken;
	private String nickname;
}
