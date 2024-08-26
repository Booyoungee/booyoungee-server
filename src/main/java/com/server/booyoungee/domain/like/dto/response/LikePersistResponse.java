package com.server.booyoungee.domain.like.dto.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import com.server.booyoungee.domain.like.domain.Like;

import io.swagger.v3.oas.annotations.media.Schema;

public record LikePersistResponse(
	@Schema(description = "좋아요 ID", example = "1", requiredMode = REQUIRED)
	Long likeId
) {
	public static LikePersistResponse from(Like like) {
		return new LikePersistResponse(
			like.getId()
		);
	}
}
