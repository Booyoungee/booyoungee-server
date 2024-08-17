package com.server.booyoungee.domain.login.exception;

import static com.server.booyoungee.domain.login.exception.LoginExceptionCode.*;

import com.server.booyoungee.global.exception.CustomException;

public class ConflictUserException extends CustomException {
	public ConflictUserException() {
		super(CONFLICT_USER);
	}
}