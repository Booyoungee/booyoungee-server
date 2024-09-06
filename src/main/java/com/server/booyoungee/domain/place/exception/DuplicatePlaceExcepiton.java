package com.server.booyoungee.domain.place.exception;

import static com.server.booyoungee.domain.place.exception.PlaceExceptionCode.*;

import com.server.booyoungee.global.exception.CustomException;

public class DuplicatePlaceExcepiton extends CustomException {
	public DuplicatePlaceExcepiton() {
		super(DUPLICATE_PLACE);
	}
}
