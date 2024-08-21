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
	String placeId,

	@Schema(description = "장소 이름", example = "광안리해변", requiredMode = REQUIRED)
	String placeName,

	@Schema(description = "스탬프 타입", example = "STAMP", requiredMode = REQUIRED)
	String type,

	// TODO: count 필드 제거
	// @Schema(description = "스탬프 횟수", example = "3", requiredMode = REQUIRED)
	// Long count,

	@Schema(description = "생성일", example = "2021.09.01.12:00:00", requiredMode = REQUIRED)
	@JsonFormat(shape = STRING, pattern = "yyyy.MM.dd.HH:mm:ss")
	LocalDateTime createdAt,

	@Schema(description = "수정일", example = "2021.09.01.12:00:00", requiredMode = REQUIRED)
	@JsonFormat(shape = STRING, pattern = "yyyy.MM.dd.HH:mm:ss.SSS")
	LocalDateTime updatedAt
) {
	public static StampResponse of(Stamp stamp, String placeName) {
		return StampResponse.builder()
			.stampId(stamp.getStampId())
			.placeId(stamp.getPlaceId())
			.placeName(placeName)
			.type(stamp.getType())
			// .count(count)
			.createdAt(stamp.getCreatedAt())
			.updatedAt(stamp.getUpdatedAt())
			.build();
	}

}
