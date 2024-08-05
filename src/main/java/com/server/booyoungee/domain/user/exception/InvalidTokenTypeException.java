package com.server.booyoungee.domain.user.exception;

import static com.server.booyoungee.domain.user.exception.UserExceptionCode.INVALID_TOKEN_TYPE;

import com.server.booyoungee.global.exception.CustomException;

public class InvalidTokenTypeException extends CustomException {
	public InvalidTokenTypeException() {
		super(INVALID_TOKEN_TYPE);
	}
}
