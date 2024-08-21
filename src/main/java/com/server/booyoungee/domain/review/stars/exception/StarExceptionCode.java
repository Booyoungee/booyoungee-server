package com.server.booyoungee.domain.review.stars.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.springframework.http.HttpStatus;

import com.server.booyoungee.global.exception.ExceptionCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StarExceptionCode implements ExceptionCode {

	INVALID_STAR_VALUE(BAD_REQUEST, "별점은 1~5 사이의 값이어야 합니다.")
	;

	private final HttpStatus status;
	private final String message;

	@Override
	public String getCode() {
		return this.name();
	}

}
