package com.server.booyoungee.domain.login.dto.request;

import com.server.booyoungee.domain.login.domain.enums.Provider;

import jakarta.validation.constraints.NotNull;

public record LoginRequestDto(
	@NotNull Provider provider,
	String name
) {
}