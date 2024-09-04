package com.server.booyoungee.domain.place.dto.response.movie;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import java.util.List;

import com.server.booyoungee.domain.place.domain.movie.MoviePlace;

import io.swagger.v3.oas.annotations.media.Schema;

public record MoviePlaceListResponse(
	@Schema(
		description = "영화관 목록",
		example = "[{\"id\":1,\"name\":\"임랑해수욕장\",\"basicAddress\":\"부산광역시 기장군 장안읍 임랑해안길 51\",\"district\":\"기장군\",\"viewCount\":\"512\",\"movieName\":\"극비수사\",\"movieCode\":\"K14737\",\"description\":\"경찰수사팀과 가족들이 유괴범과의 두 번째 접촉을 시도하는 장면\",\"mapX\":\"129.0650146\",\"mapY\":\"35.0686809\",\"productionYear\":\"2015\"}]",
		requiredMode = REQUIRED
	)
	List<MoviePlaceResponse> contents
) {
	public static MoviePlaceListResponse of(List<MoviePlace> content) {
		return new MoviePlaceListResponse(content.stream()
			.map(MoviePlaceResponse::from)
			.toList());
	}
}
