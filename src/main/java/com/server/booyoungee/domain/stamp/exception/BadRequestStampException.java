package com.server.booyoungee.domain.stamp.exception;

import static com.server.booyoungee.domain.stamp.exception.StampExceptionCode.*;

import com.server.booyoungee.global.exception.CustomException;

public class BadRequestStampException extends CustomException {

	public BadRequestStampException() {
		super(SO_FAR);
	}
}
