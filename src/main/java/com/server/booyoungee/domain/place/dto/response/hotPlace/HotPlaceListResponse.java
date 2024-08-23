package com.server.booyoungee.domain.place.dto.response.hotPlace;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

public record HotPlaceListResponse(

	@Schema(
		description = "핫한 여행지 목록",
		example = "[{\"placeId\":1,\"type\":\"MOVIE\",\"name\":\"임랑해수욕장\",\"updatedAt\":\"2021-08-10T15:00:00.000\",\"viewCount\":1}]",
		requiredMode = REQUIRED

	)
	List<HotPlaceResponse> contents
) {
	public static HotPlaceListResponse of(List<HotPlaceResponse> content) {
		return new HotPlaceListResponse(content);
	}
}
