package com.server.booyoungee.domain.login.exception;

import static com.server.booyoungee.domain.login.exception.LoginExceptionCode.*;

import com.server.booyoungee.global.exception.CustomException;

public class NotFoundUserException extends CustomException {
	public NotFoundUserException() {
		super(NOT_FOUND_USER);
	}
}
