package com.server.booyoungee.domain.place.exception.tour;

import static com.server.booyoungee.domain.place.exception.tour.TourInfoExceptionCode.OPEN_API_CALL_ERROR;

import com.server.booyoungee.global.exception.CustomException;

public class OpenApiCallErrorException extends CustomException {
	public OpenApiCallErrorException() {
		super(OPEN_API_CALL_ERROR);
	}
}
