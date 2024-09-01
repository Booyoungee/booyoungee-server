package com.server.booyoungee.domain.login.dto.request;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record NickNameRequestDto(
	@Schema(description = "닉네임", example = "홍길동", requiredMode = REQUIRED)
	@NotNull
	String nickname

) {
}
