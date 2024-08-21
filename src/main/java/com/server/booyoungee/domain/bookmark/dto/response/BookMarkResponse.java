package com.server.booyoungee.domain.bookmark.dto.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import com.server.booyoungee.domain.place.domain.PlaceType;

import io.swagger.v3.oas.annotations.media.Schema;

public record BookMarkResponse(
	@Schema(description = "북마크 ID", example = "1", requiredMode = REQUIRED)
	Long id,

	@Schema(description = "장소명", example = "임랑해수욕장", requiredMode = REQUIRED)
	String name,

	@Schema(description = "위도", example = "35.0686809", requiredMode = REQUIRED)
	Double latitude,

	@Schema(description = "경도", example = "129.0650146", requiredMode = REQUIRED)
	Double longtidude,

	@Schema(description = "장소 타입", example = "MOVIE", requiredMode = REQUIRED)
	PlaceType type,

	@Schema(description = "장소 카테고리", example = "영화 촬영지", requiredMode = REQUIRED)
	String placeCategory
) {
	public static BookMarkResponse of(Long id, String name, Double latitude, Double longtidude, PlaceType type,
		String placeCategory) {
		return new BookMarkResponse(id, name, latitude, longtidude, type, placeCategory);
	}
}
