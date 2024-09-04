package com.server.booyoungee.domain.place.dto.response.store;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import java.util.List;

import com.server.booyoungee.global.common.PageableResponse;

import io.swagger.v3.oas.annotations.media.Schema;

public record StorePlacePageResponse<T>(
	@Schema(
		description = "장소 목록",
		example = "[{\"id\":22,\"name\":\"태화육개장\",\"basicAddress\":\"부산광역시 부산진구 서면문화로 18(부전동, 1층)\",\"district\":\"부산진구\",\"viewCount\":\"0\",\"storeId\":887,\"detailAddress\":\"1층 태화육개장\",\"tel\":\"051-803-8320\",\"mainBusiness\":\"육개장\",\"mapX\":\"129.056980120201\",\"mapY\":\"35.1592040842729\",\"placeType\":\"STORE\"}]",
		requiredMode = REQUIRED
	)
	List<StorePlaceResponse> contents,

	@Schema(description = "페이징 정보", requiredMode = REQUIRED)
	PageableResponse<T> pageable
) {
	public static <T> StorePlacePageResponse<T> of(List<StorePlaceResponse> content,
		PageableResponse<T> pageableResponse) {
		return new StorePlacePageResponse<>(content, pageableResponse);
	}
}
