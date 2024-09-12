package com.server.booyoungee.domain.user.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import com.server.booyoungee.global.exception.ExceptionCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserExceptionCode implements ExceptionCode {
	INVALID_TOKEN_TYPE(BAD_REQUEST, "유효하지 않은 토큰 타입입니다"),
	EXPIRED_JWT_TOKEN(BAD_REQUEST, "만료된 토큰입니다"),
	NOT_FOUND_USER(NOT_FOUND, "유저를 찾을 수 없습니다"),
	EMPTY_PRINCIPAL(NOT_FOUND, "토큰에 유저 정보가 없습니다"),
	DUPLICATE_NICKNAME(CONFLICT, "이미 사용 중인 닉네임입니다"),
	DUPLICATE_BLOCK_USER(CONFLICT, "이미 차단한 유저입니다");

	private final HttpStatus status;
	private final String message;

	@Override
	public String getCode() {
		return this.name();
	}
}
