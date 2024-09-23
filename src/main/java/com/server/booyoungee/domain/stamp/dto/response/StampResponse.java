package com.server.booyoungee.domain.stamp.dto.response;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.*;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.server.booyoungee.domain.place.dto.response.movie.MoviePlaceResponse;
import com.server.booyoungee.domain.stamp.domain.Stamp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record StampResponse(
	@Schema(description = "스탬프 ID", example = "1", requiredMode = REQUIRED)
	Long stampId,

	@Schema(description = "장소 ID", example = "1", requiredMode = REQUIRED)
	Long placeId,

	@Schema(description = "장소 이름", example = "광안리해변", requiredMode = REQUIRED)
	String placeName,

	@Schema(description = "스탬프 타입", example = "STAMP", requiredMode = REQUIRED)
	String type,

	@Schema(description = "이미지 URL", example = "[\"https://booyoungee.s3.ap-northeast-2.amazonaws.com/임랑해수욕장1.jpg\",\"https://booyoungee.s3.ap-northeast-2.amazonaws.com/임랑해수욕장2.jpg\"]", requiredMode = REQUIRED)
	List<String> images,

	@Schema(description = "생성일", example = "2021.09.01.12:00:00", requiredMode = REQUIRED)
	@JsonFormat(shape = STRING, pattern = "yyyy.MM.dd.HH:mm:ss.SSS")
	LocalDateTime createdAt,

	@Schema(description = "수정일", example = "2021.09.01.12:00:00", requiredMode = REQUIRED)
	@JsonFormat(shape = STRING, pattern = "yyyy.MM.dd.HH:mm:ss.SSS")
	LocalDateTime updatedAt,

	@Schema(description = "경도", example = "129.0650146", requiredMode = REQUIRED)
	String mapX,

	@Schema(description = "위도", example = "35.0686809", requiredMode = REQUIRED)
	String mapY

) {
	public static StampResponse from(Stamp stamp, MoviePlaceResponse moviePlace, List<String> images) {
		return StampResponse.builder()
			.stampId(stamp.getStampId())
			.placeId(stamp.getPlace().getId())
			.placeName(stamp.getPlace().getName())
			.type(stamp.getType())
			.mapX(moviePlace.mapX())
			.mapY(moviePlace.mapY())
			// .count(count)
			.createdAt(stamp.getCreatedAt())
			.updatedAt(stamp.getUpdatedAt())
			.images(images)
			.build();
	}

	static public StampResponse of(Long stampId, LocalDateTime createdAt, LocalDateTime updatedAt, Long placeId,
		String placeName, String type, List<String> images, String mapX, String mapY) {
		return StampResponse.builder()
			.stampId(stampId)
			.placeId(placeId)
			.placeName(placeName)
			.type(type)
			.createdAt(createdAt)
			.updatedAt(updatedAt)
			.mapX(mapX)
			.mapY(mapY)
			.images(images)
			.build();
	}

	static public StampResponse of(Long placeId,
		String placeName, String type, List<String> images, String mapX, String mapY) {
		return StampResponse.builder()
			.placeId(placeId)
			.placeName(placeName)
			.type(type)
			.images(images)
			.mapX(mapX)
			.mapY(mapY)
			.build();
	}

}
