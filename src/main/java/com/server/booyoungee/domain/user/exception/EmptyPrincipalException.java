package com.server.booyoungee.domain.user.exception;

import static com.server.booyoungee.domain.user.exception.UserExceptionCode.EMPTY_PRINCIPAL;

import com.server.booyoungee.global.exception.CustomException;

public class EmptyPrincipalException extends CustomException {
	public EmptyPrincipalException() {
		super(EMPTY_PRINCIPAL);
	}
}
