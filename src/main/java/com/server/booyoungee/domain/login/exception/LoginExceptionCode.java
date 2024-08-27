package com.server.booyoungee.domain.login.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import com.server.booyoungee.global.exception.ExceptionCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoginExceptionCode implements ExceptionCode {

	NOT_FOUND_USER_INFO(BAD_REQUEST, "유저 정보를 불러올 수 없습니다."),
	NOT_FOUND_USER(NOT_FOUND, "존재하지 않는 유저입니다."),
	CONFLICT_USER(CONFLICT, "이미 존재하는 유저입니다.");

	private final HttpStatus status;
	private final String message;

	@Override
	public String getCode() {
		return this.name();
	}
}
