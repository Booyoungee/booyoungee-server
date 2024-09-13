package com.server.booyoungee.domain.bookmark.dto.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import com.server.booyoungee.domain.bookmark.domain.BookMark;

import io.swagger.v3.oas.annotations.media.Schema;

public record BookMarkPersistResponse(
	@Schema(description = "북마크 ID", example = "1", requiredMode = REQUIRED)
	Long bookmarkId,

	@Schema(description = "장소 ID", example = "1", requiredMode = REQUIRED)
	Long placeId
) {
	public static BookMarkPersistResponse from(BookMark bookmark) {
		return new BookMarkPersistResponse(
			bookmark.getBookMarkId(),
			bookmark.getPlaceId().getId()
		);
	}
}


