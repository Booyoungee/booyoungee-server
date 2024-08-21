package com.server.booyoungee.global.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GlobalExceptionCode implements ExceptionCode{
	EXCEL_READ_ERROR(NOT_FOUND, "엑셀 파일을 찾을 수 없거나 읽는 중 오류가 발생했습니다."),
	INVALID_INPUT(BAD_REQUEST, "유효한 입력 형식이 아닙니다."),
	SERVER_ERROR(INTERNAL_SERVER_ERROR, "예상치 못한 문제가 발생했습니다.");
	;

	private final HttpStatus status;
	private final String message;

	@Override
	public String getCode() {
		return this.name();
	}

}