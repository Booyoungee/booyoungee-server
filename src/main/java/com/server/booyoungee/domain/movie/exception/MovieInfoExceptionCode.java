package com.server.booyoungee.domain.movie.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import com.server.booyoungee.global.exception.ExceptionCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MovieInfoExceptionCode implements ExceptionCode {

	NOT_FOUND_MOVIE_INFO(NOT_FOUND, "해당 영화정보를 찾을 수 없습니다.");

	private final HttpStatus status;
	private final String message;

	@Override
	public String getCode() {
		return this.name();
	}
}
