package com.server.booyoungee.domain.place.dto.response.store;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import java.util.List;

import com.server.booyoungee.global.common.PageableResponse;

import io.swagger.v3.oas.annotations.media.Schema;

public record StorePlacePageResponse<T>(
	@Schema(
		description = "장소 목록",
		example = "[{\"id\":1,\"name\":\"희망통닭\",\"basicAddress\":\"부산광역시 동래구 명륜로\",\"district\":\"동래구\",\"viewCount\":\"512\",\"storeId\":550,\"detailAddress\":\"98번길 94 희망통닭\",\"tel\":\"051-555-0000\",\"mainBusiness\":\"치킨\"}]",
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
