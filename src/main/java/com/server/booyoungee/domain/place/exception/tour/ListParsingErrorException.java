package com.server.booyoungee.domain.place.exception.tour;

import static com.server.booyoungee.domain.place.exception.tour.TourInfoExceptionCode.LIST_PARSING_ERROR;

import com.server.booyoungee.global.exception.CustomException;

public class ListParsingErrorException extends CustomException {

	public ListParsingErrorException() {
		super(LIST_PARSING_ERROR);
	}
}
