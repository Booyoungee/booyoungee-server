package com.server.booyoungee.domain.place.dto.response.store;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import java.util.List;

import com.server.booyoungee.domain.place.domain.store.StorePlace;

import io.swagger.v3.oas.annotations.media.Schema;

public record StorePlaceListResponse(
	@Schema(
		description = "장소 목록",
		example = "[{\"id\":22,\"name\":\"태화육개장\",\"basicAddress\":\"부산광역시 부산진구 서면문화로 18(부전동, 1층)\",\"district\":\"부산진구\",\"viewCount\":\"0\",\"storeId\":887,\"detailAddress\":\"1층 태화육개장\",\"tel\":\"051-803-8320\",\"mainBusiness\":\"육개장\",\"mapY\":\"129.056980120201\",\"mapX\":\"35.1592040842729\",\"placeType\":\"STORE\"}]",
		requiredMode = REQUIRED
	)
	List<StorePlaceResponse> contents
) {
	public static StorePlaceListResponse of(List<StorePlace> content) {
		return new StorePlaceListResponse(content.stream()
			.map(StorePlaceResponse::from)
			.toList());
	}
}
