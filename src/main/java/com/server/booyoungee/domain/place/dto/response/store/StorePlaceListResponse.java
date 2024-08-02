package com.server.booyoungee.domain.place.dto.response.store;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

public record StorePlaceListResponse(
	@Schema(
		description = "장소 목록",
		example = "[{\"id\":1,\"name\":\"희망통닭\",\"basicAddress\":\"부산광역시 동래구 명륜로\",\"district\":\"동래구\",\"viewCount\":\"512\",\"storeId\":550,\"detailAddress\":\"98번길 94 희망통닭\",\"tel\":\"051-555-0000\",\"mainBusiness\":\"치킨\"}]",
		requiredMode = REQUIRED
	)
	List<StorePlaceResponse> contents
) {
	public static StorePlaceListResponse of(List<StorePlaceResponse> content) {
		return new StorePlaceListResponse(content);
	}
}
