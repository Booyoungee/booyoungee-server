package com.server.booyoungee.domain.like.dto.request;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record LikeRequest(
	@Schema(description = "장소 ID", example = "1", requiredMode = REQUIRED)
	@NotNull @Positive
	Long placeId
) {
}
