package com.server.booyoungee.domain.bookmark.dto.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

public record BookMarkListResponse(

	@Schema(
		description = "북마크 목록",
		example = "[{\"id\":1,\"placeId\":1,\"contentId\":\"27891\",\"name\":\"임랑해수욕장\",\"mapX\":\"129.0650146\",\"mapY\":\"35.0686809\",\"type\":\"MOVIE\",\"placeCategory\":\"영화 촬영지\"}]",
		requiredMode = REQUIRED
	)
	List<BookMarkResponse> contents
) {
	public static BookMarkListResponse of(List<BookMarkResponse> content) {
		return new BookMarkListResponse(content);
	}
}
