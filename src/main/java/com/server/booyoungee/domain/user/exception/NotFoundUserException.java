package com.server.booyoungee.domain.user.exception;

import static com.server.booyoungee.domain.user.exception.UserExceptionCode.NOT_FOUND_USER;

import com.server.booyoungee.global.exception.CustomException;

public class NotFoundUserException extends CustomException {
	public NotFoundUserException() {
		super(NOT_FOUND_USER);
	}
}
