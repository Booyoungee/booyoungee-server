package com.server.booyoungee.domain.user.exception;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.http.HttpStatus;

import com.server.booyoungee.global.exception.ExceptionCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserExceptionCode implements ExceptionCode {
	NOT_FOUND_USER(NOT_FOUND, "유저를 찾을 수 없습니다"),
	DUPLICATE_NICKNAME(CONFLICT, "이미 사용 중인 닉네임입니다"),
	;

	private final HttpStatus status;
	private final String message;

	@Override
	public String getCode() {
		return this.name();
	}
}
