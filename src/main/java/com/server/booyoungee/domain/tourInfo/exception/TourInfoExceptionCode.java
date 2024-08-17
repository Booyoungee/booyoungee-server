package com.server.booyoungee.domain.tourInfo.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.springframework.http.HttpStatus;

import com.server.booyoungee.global.exception.ExceptionCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TourInfoExceptionCode implements ExceptionCode {

	OPEN_API_CALL_ERROR(BAD_REQUEST, "오픈 API 호출 중 오류가 발생했습니다."),
	LIST_PARSING_ERROR(BAD_REQUEST, "List 파싱 중 오류가 발생했습니다."),
	;

	private final HttpStatus status;
	private final String message;

	@Override
	public String getCode() {
		return this.name();
	}
}
