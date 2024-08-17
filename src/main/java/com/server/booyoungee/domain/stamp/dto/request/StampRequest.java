package com.server.booyoungee.domain.stamp.dto.request;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import io.swagger.v3.oas.annotations.media.Schema;

public record StampRequest(

	@Schema(description = "장소 id", example = "1", requiredMode = REQUIRED)
	String placeId,

	@Schema(description = "스탬프 타입", example = "예시 타입", requiredMode = REQUIRED)
	String type,

	@Schema(description = "현재 위치 x", example = "35.192", requiredMode = REQUIRED)
	double userX,

	@Schema(description = "현재 위치 y", example = "129.192", requiredMode = REQUIRED)
	double userY,

	@Schema(description = "x", example = "35.19284", requiredMode = REQUIRED)
	double x,

	@Schema(description = "y", example = "129.1222", requiredMode = REQUIRED)
	double y
) {
}
