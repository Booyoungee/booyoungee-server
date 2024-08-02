package com.server.booyoungee.domain.place.dto.response.movie;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import com.server.booyoungee.domain.place.domain.moviePlace.MoviePlace;
import com.server.booyoungee.global.common.PlaceType;

import io.swagger.v3.oas.annotations.media.Schema;

public record MoviePlaceResponse(
	@Schema(description = "장소 ID", example = "1", requiredMode = REQUIRED)
	Long id,

	@Schema(description = "장소명", example = "임랑해수욕장", requiredMode = REQUIRED)
	String name,

	@Schema(description = "기본 주소", example = "부산광역시 기장군 장안읍 임랑해안길 51", requiredMode = REQUIRED)
	String basicAddress,

	@Schema(description = "군/구", example = "기장군", requiredMode = REQUIRED)
	String district,

	@Schema(description = "조회수", example = "512", requiredMode = REQUIRED)
	int viewCount,

	@Schema(description = "영화 제목", example = "극비 수사", requiredMode = REQUIRED)
	String movieName,

	@Schema(description = "영화 코드", example = "K14737", requiredMode = REQUIRED)
	String movieCode,

	@Schema(description = "장면 설명", example = "경찰수사팀과 가족들이 유괴범과의 두 번째 접촉을 시도하는 장면", requiredMode = REQUIRED)
	String description,

	@Schema(description = "위도", example = "35.0686809", requiredMode = REQUIRED)
	String mapX,

	@Schema(description = "경도", example = "129.0650146", requiredMode = REQUIRED)
	String mapY,

	@Schema(description = "제작년도", example = "2015", requiredMode = REQUIRED)
	String productionYear,

	@Schema(description = "장소 타입", example = "MOVIE", requiredMode = REQUIRED)
	PlaceType placeType

) {
	public static MoviePlaceResponse from(final MoviePlace moviePlace) {
		return new MoviePlaceResponse(
			moviePlace.getId(),
			moviePlace.getName(),
			moviePlace.getBasicAddress(),
			moviePlace.getDistrict(),
			moviePlace.getViewCount(),
			moviePlace.getMovieName(),
			moviePlace.getMovieCode(),
			moviePlace.getDescription(),
			moviePlace.getMapX(),
			moviePlace.getMapY(),
			moviePlace.getProductionYear(),
			PlaceType.MOVIE
		);
	}

}
