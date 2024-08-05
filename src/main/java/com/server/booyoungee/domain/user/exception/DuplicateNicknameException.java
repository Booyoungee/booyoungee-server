package com.server.booyoungee.domain.user.exception;

import static com.server.booyoungee.domain.user.exception.UserExceptionCode.DUPLICATE_NICKNAME;

import com.server.booyoungee.global.exception.CustomException;

public class DuplicateNicknameException extends CustomException {
	public DuplicateNicknameException() {
		super(DUPLICATE_NICKNAME);
	}
}
