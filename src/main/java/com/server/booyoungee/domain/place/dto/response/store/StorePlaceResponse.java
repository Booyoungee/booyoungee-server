package com.server.booyoungee.domain.place.dto.response.store;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import com.server.booyoungee.domain.place.domain.store.StorePlace;
import com.server.booyoungee.global.common.PlaceType;

import io.swagger.v3.oas.annotations.media.Schema;

public record StorePlaceResponse(
	@Schema(description = "장소 ID", example = "1", requiredMode = REQUIRED)
	Long id,

	@Schema(description = "장소명", example = "희망통닭", requiredMode = REQUIRED)
	String name,

	@Schema(description = "기본 주소", example = "부산광역시 동래구 명륜로", requiredMode = REQUIRED)
	String basicAddress,

	@Schema(description = "군/구", example = "동래구", requiredMode = REQUIRED)
	String district,

	@Schema(description = "조회수", example = "512", requiredMode = REQUIRED)
	int viewCount,

	@Schema(description = "식당 고유 ID", example = "550", requiredMode = REQUIRED)
	Long storeId,

	@Schema(description = "상세 주소", example = "98번길 94 희망통닭", requiredMode = REQUIRED)
	String detailAddress,

	@Schema(description = "식당 전화번호", example = "051-555-0000", requiredMode = REQUIRED)
	String tel,

	@Schema(description = "주요 메뉴", example = "치킨", requiredMode = REQUIRED)
	String mainBusiness,

	@Schema(description = "경도", example = "129.0650146", requiredMode = REQUIRED)
	String mapX,

	@Schema(description = "위도", example = "35.0686809", requiredMode = REQUIRED)
	String mapY,

	@Schema(description = "장소 타입", example = "STORE", requiredMode = REQUIRED)
	PlaceType placeType
) {
	public static StorePlaceResponse from(final StorePlace storePlace) {
		return new StorePlaceResponse(
			storePlace.getId(),
			storePlace.getName(),
			storePlace.getBasicAddress(),
			storePlace.getDistrict(),
			storePlace.getViewCount(),
			storePlace.getStoreId(),
			storePlace.getDetailAddress(),
			storePlace.getTel(),
			storePlace.getMainBusiness(),
			storePlace.getMapX(),
			storePlace.getMapY(),
			PlaceType.STORE
		);
	}
}
