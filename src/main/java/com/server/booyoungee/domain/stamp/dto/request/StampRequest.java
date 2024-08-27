package com.server.booyoungee.domain.stamp.dto.request;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record StampRequest(

	@Schema(description = "장소 id", example = "1", requiredMode = REQUIRED)
	@NotNull @Positive
	Long placeId,

	@Schema(description = "스탬프 타입", example = "예시 타입(movie)", requiredMode = REQUIRED)
	@NotNull
	String type,

	@Schema(description = "현재 위치 x", example = "129.1222", requiredMode = REQUIRED)
	@NotBlank
	String userX,

	@Schema(description = "현재 위치 y", example = "35.192", requiredMode = REQUIRED)
	@NotNull
	String userY,

	@Schema(description = "x", example = "129.1222", requiredMode = REQUIRED)
	@NotBlank
	String x,

	@Schema(description = "y", example = "35.192", requiredMode = REQUIRED)
	@NotBlank
	String y
) {
}
