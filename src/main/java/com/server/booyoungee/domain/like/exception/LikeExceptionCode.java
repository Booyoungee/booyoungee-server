package com.server.booyoungee.domain.like.exception;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.http.HttpStatus;

import com.server.booyoungee.global.exception.ExceptionCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LikeExceptionCode implements ExceptionCode {
	USER_NOT_LIKE_OWNER(FORBIDDEN, "좋아요를 등록한 유저가 아닙니다."),
	NOT_FOUND_LIKE(NOT_FOUND, "좋아요를 찾을 수 없습니다."),
	;

	private final HttpStatus status;
	private final String message;

	@Override
	public String getCode() {
		return this.name();
	}
}
