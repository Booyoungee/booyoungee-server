package com.server.booyoungee.domain.review.comment.exception;

import static com.server.booyoungee.domain.review.comment.exception.CommentExceptionCode.USER_NOT_WRITER_OF_COMMENT;

import com.server.booyoungee.global.exception.CustomException;

public class UserNotWriterOfCommentException extends CustomException {
	public UserNotWriterOfCommentException() {
		super(USER_NOT_WRITER_OF_COMMENT);
	}
}
