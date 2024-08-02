package com.server.booyoungee.domain.place.dto.response.movie;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import java.util.List;

import com.server.booyoungee.global.common.PageableResponse;

import io.swagger.v3.oas.annotations.media.Schema;

public record MoviePlacePageResponse<T>(
	@Schema(
		description = "영화관 목록",
		example = "[{\"id\":1,\"name\":\"임랑해수욕장\",\"basicAddress\":\"부산광역시 기장군 장안읍 임랑해안길 51\",\"district\":\"기장군\",\"viewCount\":\"512\",\"movieName\":\"극비수사\",\"movieCode\":\"K14737\",\"description\":\"경찰수사팀과 가족들이 유괴범과의 두 번째 접촉을 시도하는 장면\",\"mapX\":\"35.0686809\",\"mapY\":\"129.0650146\",\"productionYear\":\"2015\"}]",
		requiredMode = REQUIRED
	)
	List<MoviePlaceResponse> contents,

	@Schema(description = "페이징 정보", requiredMode = REQUIRED)
	PageableResponse<T> pageable
) {
	public static <T> MoviePlacePageResponse<T> of(List<MoviePlaceResponse> content,
		PageableResponse<T> pageableResponse) {
		return new MoviePlacePageResponse<>(content, pageableResponse);
	}
}
