package com.server.booyoungee.global.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GlobalExceptionCode implements ExceptionCode{

	// BAD_REQUEST_ERROR(HttpStatus.BAD_REQUEST),
	// NOT_FOUND_ERROR(HttpStatus.NOT_FOUND),
	// NULL_POINT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR),
	// IO_ERROR(HttpStatus.INTERNAL_SERVER_ERROR),
	// INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR),
	// EMPTY_PRINCIPAL(HttpStatus.UNAUTHORIZED),
	// UNAUTHORIZED_ERROR(HttpStatus.UNAUTHORIZED),
	// JWT_IS_EMPTY(HttpStatus.UNAUTHORIZED),
	// INVALID_TOKEN_TYPE(HttpStatus.UNAUTHORIZED),
	// INVALID_JWT(HttpStatus.UNAUTHORIZED),
	// EXPIRED_JWT(HttpStatus.UNAUTHORIZED),
	// UNSUPPORTED_JWT(HttpStatus.UNAUTHORIZED),
	// DUPLICATE_ERROR(HttpStatus.CONFLICT),
	// EXCEL_READ_ERROR(HttpStatus.INTERNAL_SERVER_ERROR);
	EXCEL_READ_ERROR(NOT_FOUND, "엑셀 파일을 찾을 수 없거나 읽는 중 오류가 발생했습니다."),
	;

	private final HttpStatus status;
	private final String message;

	@Override
	public String getCode() {
		return this.name();
	}

}