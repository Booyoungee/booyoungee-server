package com.server.booyoungee.domain.place.dto.response.recommend;

import io.swagger.v3.oas.annotations.media.Schema;

public record RecommendPersistResponse(
		@Schema
			(
				description = "장소 추천 ID",
				example = "1",
				required = true
			)
		Long id,
		@Schema
			(
				description = "장소 아이디, 장소 조회는 다음 ID를 참조하여 사용합니다.",
				example = "1",
				required = true
			)
		Long placeId
) {
	public static RecommendPersistResponse of(Long id, Long placeId) {
		return new RecommendPersistResponse(
			id,
			placeId
		);
	}
}
