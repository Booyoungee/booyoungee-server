package com.server.booyoungee.domain.stamp.dto;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.*;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record PlaceStampCountDto(
	@Schema(description = "장소 ID", example = "1", requiredMode = REQUIRED)
	String placeId,
	@Schema(description = "스탬프 횟수", example = "3", requiredMode = REQUIRED)
	long count,
	@Schema(description = "스탬프 타입", example = "STAMP", requiredMode = REQUIRED)
	String type,
	@Schema(description = "생성일", example = "2021.09.01.12:00:00", requiredMode = REQUIRED)
	@JsonFormat(shape = STRING, pattern = "yyyy.MM.dd.HH:mm:ss")
	LocalDateTime createdAt,

	@Schema(description = "수정일", example = "2021.09.01.12:00:00", requiredMode = REQUIRED)
	@JsonFormat(shape = STRING, pattern = "yyyy.MM.dd.HH:mm:ss.SSS")
	LocalDateTime updatedAt
) {

}