package com.server.booyoungee.domain.stamp.dto.request;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import io.swagger.v3.oas.annotations.media.Schema;

public record StampRequest(

	@Schema(description = "장소 id", example = "1", requiredMode = REQUIRED)
	Long placeId,

	@Schema(description = "스탬프 타입", example = "예시 타입(movie)", requiredMode = REQUIRED)
	String type,

	@Schema(description = "현재 위치 x", example = "129.1222", requiredMode = REQUIRED)
	double userX,

	@Schema(description = "현재 위치 y", example = "35.192", requiredMode = REQUIRED)
	double userY,

	@Schema(description = "x", example = "129.1222", requiredMode = REQUIRED)
	double x,

	@Schema(description = "y", example = "35.192", requiredMode = REQUIRED)
	double y
) {
}
