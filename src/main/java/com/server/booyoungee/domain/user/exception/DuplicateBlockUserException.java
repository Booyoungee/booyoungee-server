package com.server.booyoungee.domain.user.exception;

import static com.server.booyoungee.domain.user.exception.UserExceptionCode.*;

import com.server.booyoungee.global.exception.CustomException;

public class DuplicateBlockUserException extends CustomException {
	public DuplicateBlockUserException() {
		super(DUPLICATE_BLOCK_USER);
	}
}
