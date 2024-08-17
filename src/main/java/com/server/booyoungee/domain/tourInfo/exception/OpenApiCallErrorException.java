package com.server.booyoungee.domain.tourInfo.exception;

import static com.server.booyoungee.domain.tourInfo.exception.TourInfoExceptionCode.OPEN_API_CALL_ERROR;

import com.server.booyoungee.global.exception.CustomException;

public class OpenApiCallErrorException extends CustomException {
	public OpenApiCallErrorException() {
		super(OPEN_API_CALL_ERROR);
	}
}
