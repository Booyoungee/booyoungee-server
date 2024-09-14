package com.server.booyoungee.domain.review.comment.dto.request;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import com.server.booyoungee.domain.place.domain.PlaceType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record CommentRequest(
	@Schema(description = "장소 ID", example = "1", requiredMode = REQUIRED)
	@NotNull @Positive
	Long placeId,

	@Schema(description = "리뷰 내용", example = "좋아요", requiredMode = REQUIRED)
	@NotBlank
	@Size(max = 200)
	String content,

	@Schema(description = "별점", example = "5", requiredMode = REQUIRED)
	@PositiveOrZero @Max(value = 5)
	int stars,

	@Schema(description = "장소 타입", example = "movie", requiredMode = REQUIRED)
	@NotNull
	PlaceType type
) {
}
