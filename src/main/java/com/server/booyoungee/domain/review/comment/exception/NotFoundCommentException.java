package com.server.booyoungee.domain.review.comment.exception;

import static com.server.booyoungee.domain.review.comment.exception.CommentExceptionCode.NOT_FOUND_COMMENT;

import com.server.booyoungee.global.exception.CustomException;

public class NotFoundCommentException extends CustomException {
	public NotFoundCommentException() {
		super(NOT_FOUND_COMMENT);
	}
}
