package com.server.booyoungee.domain.user.dto.response;

import lombok.Builder;

@Builder
public record UserResponse(
	Long userId,
	String nickname
) {
	public static UserResponse of(Long userId, String nickname) {
		return UserResponse.builder()
			.userId(userId)
			.nickname(nickname)
			.build();
	}
}
