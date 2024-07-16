package com.server.booyoungee.domain.login.dto;

public record SocialInfoDto(String serialId, String email, String name) {
	public static SocialInfoDto of(String serialId, String email, String name) {
		return new SocialInfoDto(serialId, email, name);
	}
}