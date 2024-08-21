package com.server.booyoungee.domain.review.comment.exception;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.http.HttpStatus;

import com.server.booyoungee.global.exception.ExceptionCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommentExceptionCode implements ExceptionCode {

	USER_NOT_WRITER_OF_COMMENT(FORBIDDEN, "해당 댓글을 작성한 유저가 아닙니다."),
	NOT_FOUND_COMMENT(NOT_FOUND, "해당 리뷰가 존재하지 않습니다."),
	;

	private final HttpStatus status;
	private final String message;

	@Override
	public String getCode() {
		return this.name();
	}
}
