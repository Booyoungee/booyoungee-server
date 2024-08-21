package com.server.booyoungee.domain.review.comment.dto.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import com.server.booyoungee.domain.review.comment.domain.Comment;

import io.swagger.v3.oas.annotations.media.Schema;

public record CommentPersistResponse(
	@Schema(description = "댓글 ID", example = "1", requiredMode = REQUIRED)
	Long commentId
) {
	public static CommentPersistResponse from(Comment comment) {
		return new CommentPersistResponse(
			comment.getId()
		);
	}
}
