package com.server.booyoungee.domain.tourInfo.exception;

import static com.server.booyoungee.domain.tourInfo.exception.TourInfoExceptionCode.LIST_PARSING_ERROR;

import com.server.booyoungee.global.exception.CustomException;

public class ListParsingErrorException extends CustomException {

	public ListParsingErrorException() {
		super(LIST_PARSING_ERROR);
	}
}
