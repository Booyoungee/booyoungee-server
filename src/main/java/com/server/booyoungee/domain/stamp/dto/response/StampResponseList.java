package com.server.booyoungee.domain.stamp.dto.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

public record StampResponseList(
	@Schema(
		description = "스탬프 목록",
		example = "[{\"stampId\":1,\"placeId\":\"1\",\"placeName\":\"광안리해변\",\"type\":\"STAMP\",\"count\":3,\"createdAt\":\"2021.09.01.12:00:00\",\"updatedAt\":\"2021.09.01.12:00:00\"}]",
		requiredMode = REQUIRED
	)
	List<StampResponse> contents
) {
	public static StampResponseList from (List<StampResponse> contents) {
		return new StampResponseList(contents);
	}
}
