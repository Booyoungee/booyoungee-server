package com.server.booyoungee.domain.stamp.dto.response;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.*;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
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

	@Schema(description = "생성일", example = "2021.09.01.12:00:00", requiredMode = REQUIRED)
	@JsonFormat(shape = STRING, pattern = "yyyy.MM.dd.HH:mm:ss.SSS")
	LocalDateTime createdAt,

	@Schema(description = "수정일", example = "2021.09.01.12:00:00", requiredMode = REQUIRED)
	@JsonFormat(shape = STRING, pattern = "yyyy.MM.dd.HH:mm:ss.SSS")
	LocalDateTime updatedAt
) {
	public static StampResponse from(Stamp stamp) {
		return StampResponse.builder()
			.stampId(stamp.getStampId())
			.placeId(stamp.getPlace().getId())
			.placeName(stamp.getPlace().getName())
			.type(stamp.getType())
			// .count(count)
			.createdAt(stamp.getCreatedAt())
			.updatedAt(stamp.getUpdatedAt())
			.build();
	}

	static public StampResponse of(Long stampId, LocalDateTime createdAt, LocalDateTime updatedAt, Long placeId,
		String placeName, String type) {
		return StampResponse.builder()
			.stampId(stampId)
			.placeId(placeId)
			.placeName(placeName)
			.type(type)
			// .count(count)
			.createdAt(createdAt)
			.updatedAt(updatedAt)
			.build();
	}

}
