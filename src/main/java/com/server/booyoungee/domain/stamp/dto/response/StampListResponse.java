package com.server.booyoungee.domain.stamp.dto.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

public record StampListResponse(
	@Schema(
		description = "스탬프 목록",
		example = "[{\"stampId\":1,\"placeId\":1023,\"placeName\":\"미포철길 입구\",\"type\":\"movie\",\"images\":[\"https://image.tmdb.org/t/p/w500/elbSAG9MZCpfZkwyV3zrrYW2zt2.jpg\",\"https://booyoungee.s3.ap-northeast-2.amazonaws.com/임랑해수욕장2.jpg\"],\"createdAt\":\"2021.09.01.12:00:00\",\"updatedAt\":\"2021.09.01.12:00:00\",\"mapX\":\"129.1715068\",\"mapY\":\"35.1593662\"}]",
		requiredMode = REQUIRED
	)
	List<StampResponse> contents
) {
	public static StampListResponse from(List<StampResponse> contents) {
		return new StampListResponse(contents);
	}
}
