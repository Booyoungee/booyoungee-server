package com.server.booyoungee.domain.place.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import com.server.booyoungee.global.exception.ExceptionCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PlaceExceptionCode implements ExceptionCode {
	NOT_FOUND_PLACE(NOT_FOUND, "장소 정보를 찾을 수 없습니다."),
	DUPLICATE_PLACE(CONFLICT, "이미 등록된 장소입니다."),
	;

	private final HttpStatus status;
	private final String message;

	@Override
	public String getCode() {
		return this.name();
	}
}
