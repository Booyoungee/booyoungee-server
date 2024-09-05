package com.server.booyoungee.domain.place.dto.response.recommend;

public record RecommendPersistResponse(
	Long id,
	Long placeId
) {
	public static RecommendPersistResponse of(Long id, Long placeId) {
		return new RecommendPersistResponse(
			id,
			placeId
		);
	}
}
