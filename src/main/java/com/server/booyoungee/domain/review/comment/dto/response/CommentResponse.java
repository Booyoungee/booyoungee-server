package com.server.booyoungee.domain.review.comment.dto.response;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.server.booyoungee.domain.review.comment.domain.Comment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record CommentResponse(
	@Schema(description = "리뷰 코멘트 ID", example = "1", requiredMode = REQUIRED)
	Long id,

	@Schema(description = "장소 ID", example = "1", requiredMode = REQUIRED)
	Long placeId,

	@Schema(description = "장소 이름", example = "부산역", requiredMode = REQUIRED)
	String placeName,

	@Schema(description = "리뷰 내용", example = "좋아요", requiredMode = REQUIRED)
	String content,

	@Schema(description = "별점", example = "5", requiredMode = REQUIRED)
	int stars,

	@Schema(description = "작성자 ID", example = "1", requiredMode = REQUIRED)
	Long writerId,

	@Schema(description = "작성자 이름", example = "홍길동", requiredMode = REQUIRED)
	String writerName,

	@Schema(description = "생성일", example = "2021.09.01.12:00:00", requiredMode = REQUIRED)
	@JsonFormat(shape = STRING, pattern = "yyyy.MM.dd.HH:mm:ss")
	LocalDateTime createdAt,

	@Schema(description = "수정일", example = "2021.09.01.12:00:00", requiredMode = REQUIRED)
	@JsonFormat(shape = STRING, pattern = "yyyy.MM.dd.HH:mm:ss.SSS")
	LocalDateTime updatedAt
) {
	public static CommentResponse from(Comment comment) {
		return CommentResponse.builder()
			.id(comment.getId())
			.placeId(comment.getPlace().getId())
			.placeName(comment.getPlace().getName())
			.content(comment.getContent())
			.stars(comment.getStars().getStars())
			.writerId(comment.getWriter().getUserId())
			.writerName(comment.getWriter().getName())
			.createdAt(comment.getCreatedAt())
			.updatedAt(comment.getUpdatedAt())
			.build();
	}
}
