package com.server.booyoungee.domain.place.exception.movie;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.http.HttpStatus;

import com.server.booyoungee.global.exception.ExceptionCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MoviePlaceExceptionCode implements ExceptionCode {
	NOT_FOUND_MOVIE_PLACE(NOT_FOUND, "영화 촬영지 장소를 찾을 수 없습니다.")
	;

	private final HttpStatus status;
	private final String message;

	@Override
	public String getCode() {
		return this.name();
	}
}
