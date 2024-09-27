package com.server.booyoungee.domain.place.dto.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import java.util.List;

import com.server.booyoungee.global.common.PageableResponse;

import io.swagger.v3.oas.annotations.media.Schema;

public record PlaceSummaryPageResponse<T>(

	@Schema(
		description = "영화 장소 요약 목록",
		example = "[{\"id\":1,\"name\":\"임랑해수욕장\",\"basicAddress\":\"부산광역시 기장군 장안읍 임랑해안길 51\",\"mapX\":\"129.244\",\"mapY\":\"35.244\",\"stars\":4.7,\"likes\":12,\"reviews\":11,\"movieName\":\"극비 수사\",\"type\":\"MOVIE\",\"images\":[\"https://booyoungee.s3.ap-northeast-2.amazonaws.com/임랑해수욕장1.jpg\",\"https://booyoungee.s3.ap-northeast-2.amazonaws.com/임랑해수욕장2.jpg\"]}]",
		requiredMode = REQUIRED
	)
	List<PlaceSummaryResponse> contents,

	@Schema(description = "페이징 정보", requiredMode = REQUIRED)
	PageableResponse<T> pageable
) {
	public static <T> PlaceSummaryPageResponse<T> of(List<PlaceSummaryResponse> content,
		PageableResponse<T> pageableResponse) {
		return new PlaceSummaryPageResponse<>(content, pageableResponse);
	}
}
