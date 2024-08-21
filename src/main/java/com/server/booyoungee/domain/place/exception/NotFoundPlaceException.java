package com.server.booyoungee.domain.place.exception;

import static com.server.booyoungee.domain.place.exception.PlaceExceptionCode.NOT_FOUND_PLACE;

import com.server.booyoungee.global.exception.CustomException;

public class NotFoundPlaceException extends CustomException {
	public NotFoundPlaceException() {
		super(NOT_FOUND_PLACE);
	}
}
