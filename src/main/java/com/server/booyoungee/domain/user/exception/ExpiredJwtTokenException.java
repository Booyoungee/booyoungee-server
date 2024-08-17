package com.server.booyoungee.domain.user.exception;

import static com.server.booyoungee.domain.user.exception.UserExceptionCode.EXPIRED_JWT_TOKEN;

import com.server.booyoungee.global.exception.CustomException;

public class ExpiredJwtTokenException extends CustomException {
	public ExpiredJwtTokenException() {
		super(EXPIRED_JWT_TOKEN);
	}
}
