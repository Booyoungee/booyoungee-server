package com.server.booyoungee.domain.place.dto.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record PlaceSummaryListResponse(

	@Schema(
		description = "영화 장소 요약 목록",
		example = "[{\"id\":1,\"name\":\"임랑해수욕장\",\"basicAddress\":\"부산광역시 기장군 장안읍 임랑해안길 51\",\"stars\":4.7,\"likes\":12,\"reviews\":11,\"movieName\":\"극비 수사\"}]",
		requiredMode = REQUIRED
	)
	List<PlaceSummaryResponse> contents
) {
	public static PlaceSummaryListResponse of(List<PlaceSummaryResponse> contents) {
		return PlaceSummaryListResponse.builder()
			.contents(contents)
			.build();
	}
}
