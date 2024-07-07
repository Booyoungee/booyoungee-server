package com.server.booyoungee.global.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

	BAD_REQUEST_ERROR(HttpStatus.BAD_REQUEST),
	NOT_FOUND_ERROR(HttpStatus.NOT_FOUND),
	NULL_POINT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR),
	IO_ERROR(HttpStatus.INTERNAL_SERVER_ERROR),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR);

	private final HttpStatus httpStatus;

}