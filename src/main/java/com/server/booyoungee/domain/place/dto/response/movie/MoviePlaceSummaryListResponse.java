package com.server.booyoungee.domain.place.dto.response.movie;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import java.util.List;

import com.fasterxml.jackson.core.JsonToken;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record MoviePlaceSummaryListResponse(

	@Schema(
		description = "영화 장소 요약 목록",
		example = "[{\"id\":1,\"name\":\"임랑해수욕장\",\"basicAddress\":\"부산광역시 기장군 장안읍 임랑해안길 51\",\"stars\":4.7,\"likes\":12,\"reviews\":11,\"movieName\":\"극비 수사\"}]",
		requiredMode = REQUIRED
	)
	List<MoviePlaceSummaryResponse> contents
) {
	public static MoviePlaceSummaryListResponse of(List<MoviePlaceSummaryResponse> contents) {
		return MoviePlaceSummaryListResponse.builder()
			.contents(contents)
			.build();
	}
}
