package com.server.booyoungee.domain.review.comment.dto.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import java.util.List;

import com.server.booyoungee.domain.review.comment.domain.Comment;

import io.swagger.v3.oas.annotations.media.Schema;

public record CommentListResponse(
	@Schema(
		description = "리뷰 코멘트 목록",
		example = "[{\"id\":1,\"placeId\":\"1\",\"placeName\":\"부산역\"\"content\":\"좋아요\",\"stars\":4.5,\"writerId\":\"1\",\"writerName\":\"홍길동\",\"createdAt\":\"2021.09.01.12:00:00\",\"updatedAt\":\"2021.09.01.12:00:00\"}]",
		requiredMode = REQUIRED
	)
	List<CommentResponse> contents,

	@Schema(description = "평균 별점", example = "4.5", requiredMode = REQUIRED)
	double averageStars
) {
	public static CommentListResponse from(List<Comment> contents) {
		return new CommentListResponse(
			contents.stream()
				.map(CommentResponse::from)
				.toList(),
			contents.stream()
				.mapToDouble(comment -> comment.getStars().getStars())
				.average()
				.orElse(0.0)
		);
	}
}
