package com.server.booyoungee.domain.place.exception.store;

import static com.server.booyoungee.domain.place.exception.store.StorePlaceExceptionCode.NOT_FOUND_STORE_PLACE;

import com.server.booyoungee.global.exception.CustomException;

public class NotFoundStorePlaceException extends CustomException {
	public NotFoundStorePlaceException() {
		super(NOT_FOUND_STORE_PLACE);
	}
}
